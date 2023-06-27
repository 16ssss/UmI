package YOUmI.domain.MBTI.service;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import YOUmI.domain.MBTI.model.entity.MbtiRelation;
import YOUmI.domain.MBTI.repository.QuestionRepositoryFromJson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import YOUmI.domain.MBTI.model.dto.Question;
import YOUmI.domain.MBTI.model.dto.TestResult;
import YOUmI.domain.MBTI.model.dto.TestItem;
import YOUmI.domain.User.model.entity.Customer;
import YOUmI.domain.MBTI.model.entity.MbtiQuestion;
import YOUmI.domain.MBTI.model.entity.MbtiSurveyResult;
import YOUmI.domain.User.repository.CustomerRepository;
import YOUmI.domain.MBTI.repository.QuestionRepository;
import YOUmI.domain.MBTI.repository.SurveyResultRepository;

@Service
@Log4j2
public class QuestionService {

    private int COUNT_PER_TYPE = 7;

    private String[] types = {"EI","NS","FT","PJ"};

    private String[] typeArray = {"I","E","S","N","F","T","J","P"};

    private String[] representativeTypes = {"E","S","T","J"};

    private Map<String,String> counterTypes = new HashMap<>();

    public QuestionService() {
        for(String type: types){
            String[] splitedTypes = type.split("");
            counterTypes.put(splitedTypes[0],splitedTypes[1]);
            counterTypes.put(splitedTypes[1],splitedTypes[0]);
        }
    }


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CustomerRepository userRepository;

    @Autowired
    private SurveyResultRepository surveyResultRepository;

    @Autowired
    private QuestionRepositoryFromJson questionRepositoryFromJson;

    public MbtiRelation getMbtiRelation(String mbti) {
        return questionRepositoryFromJson.getMbtiRelation(mbti);
    }

    public List<Question> getSurveyQuestions() {
        List<Question> questions = getAllQuestions();
        Map<String, List<Question>> candidates = new HashMap<>();
        List<Question> result = new ArrayList<>();

        for(String type: types){
            candidates.put(type, new ArrayList<Question>());
        }

        for(Question question: questions) {
            candidates.get(whichType(question.getType())).add(question);
        }

        for(String type : candidates.keySet()) {
            Set<Integer> set = new HashSet<>();
            List<Question> list = candidates.get(type);
            int size = list.size();
            while(set.size() < COUNT_PER_TYPE) {
                try {
                    SecureRandom random = SecureRandom.getInstanceStrong();
                    set.add(random.nextInt(size));
                } catch(NoSuchAlgorithmException e){
                    log.error("{}",e.getMessage());
                    return null;
                }
            }
            Iterator<Integer> iter = set.iterator();
            while(iter.hasNext()){
                result.add(list.get(iter.next()));
            }
        }

        return result;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll().stream().map(q->Question.builder().seq(q.getSeq()).type(q.getType()).question(q.getContent()).leftPhrase(q.getLeftPhrase()).rightPhrase(q.getRightPhrase()).leftImg(q.getLeftImg()).rightImg(q.getRightImg()).build()).collect(Collectors.toList());
    }

    public Question getQuestionBySeq(Integer seq) {
        MbtiQuestion q = questionRepository.findById(seq).orElse(null);

        return Question.builder()
                .seq(q.getSeq())
                .type(q.getType())
                .question(q.getContent())
                .leftPhrase(q.getLeftPhrase())
                .rightPhrase(q.getRightPhrase())
                .leftImg(q.getLeftImg())
                .rightImg(q.getRightImg())
                .build();

    }

    public boolean insertQuestions(List<Question> questions){
        Integer maxSeq = questionRepository.findMaxSeq();
        log.info("maxSeq: {}",maxSeq);
        if(maxSeq == null) return false;
        try {
            for (Question q : questions) {
                questionRepository.save(MbtiQuestion.builder().seq(maxSeq + 1).type(q.getType()).content(q.getQuestion()).leftPhrase(q.getLeftPhrase()).rightPhrase(q.getRightPhrase()).leftImg(q.getLeftImg()).rightImg(q.getRightImg()).build());
                maxSeq += 1;
            }
        } catch(Exception e){
            // 모든 exception 발생 => 추가 실패
            log.error("insert failed, {}: {}", e.getClass(),e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateQuestions(List<Question> questions) {
        try {
            log.info("to update: {}", questions.toString());
            questionRepository.saveAll(questions.stream().map(q -> MbtiQuestion.builder().seq(q.getSeq()).type(q.getType()).content(q.getQuestion()).leftPhrase(q.getLeftPhrase()).rightPhrase(q.getRightPhrase()).leftImg(q.getLeftImg()).rightImg(q.getRightImg()).build()).collect(Collectors.toList()));
        } catch(Exception e){
            // 모든 exception 발생 => 업데이트 실패
            log.error("update failed, {}: {}", e.getClass(),e.getMessage());
            return false;
        }
        return true;
    }

    public List<Integer> deleteQuestions(List<Integer> questionSequences){
        List<Integer> successes = new ArrayList<>();
        log.info("to delete: {}",questionSequences.toString());
        for(Integer seq: questionSequences){
            try {
                questionRepository.delete(MbtiQuestion.builder().seq(seq).build());
                successes.add(seq);
            } catch(Exception e){
                // 모든 exception 발생 => 삭제 실패
                log.error("question seq = {} delete failed, {}: {}",seq, e.getClass(),e.getMessage());
            }
        }
        return successes;
    }



    public String saveTestResult(String id, TestResult result) {

        Customer user = Customer.builder()
                .id(id)
                .username(result.getUsername())
                .surveyComment(result.getComment())
                .expectedMbti(result.getExpectedResult())
                .mbti(getSurveyResult(result.getItems(), result.getExpectedResult()))
                .build();


        userRepository.save(user);


        surveyResultRepository.saveAll(result.getItems()
                .stream()
                .map(item-> MbtiSurveyResult.builder().id(id).seq(Integer.valueOf(item.getSeq())).choice(Integer.valueOf(item.getChoice())).build())
                .collect(Collectors.toList()));

        result.getItems().stream().forEach(item -> {
            MbtiQuestion mbtiQuestion = questionRepository.findById(Integer.valueOf(item.getSeq())).orElse(null);
            if( mbtiQuestion != null && !(item.getLike() && item.getDislike()) ) {

                if (item.getLike()) {
                    mbtiQuestion.setLike(mbtiQuestion.getLike() + 1);
                }
                if (item.getDislike()) {
                    mbtiQuestion.setDislike(mbtiQuestion.getDislike() + 1);
                }

                questionRepository.save(mbtiQuestion);
            }
        });

        return user.getMbti();
    }

    private String whichType(String inputType) {
        String resultType = "";
        for(String type: types) {
            if(type.contains(inputType)){
                resultType = type;
                break;
            }
        }

        return resultType;
    }

    public Map<String, Long> getSurveyRatio(List<TestItem> items) {

        Map<String, Integer> selectionCountMap = new HashMap<>();

        Map<String, Long> ratioMap = new HashMap<>();


        for(String type: typeArray){
            selectionCountMap.put(type, 0);
        }

        for(TestItem item: items) {
            MbtiQuestion question = questionRepository.findById(Integer.valueOf(item.getSeq())).orElseThrow();
            String questionType = question.getType();
            String type = whichType(questionType);
            String counterType = counterTypes.get(questionType);

            Integer index = 0;
            try {
                switch (Integer.valueOf(item.getChoice())) {
                    //index={0,1,2}은 해당 질문에 긍정으로 평가해 type, index={3,4}는 부정으로 평가해 counterType
                    case -3:
                        selectionCountMap.put(questionType, selectionCountMap.get(questionType) + 1);
                        break;
                    case -1:
                        selectionCountMap.put(questionType, selectionCountMap.get(questionType) + 1);
                        break;
                    case 0:
                        selectionCountMap.put(questionType, selectionCountMap.get(questionType) + 1);
                        break;
                    case 1:
                        selectionCountMap.put(counterType, selectionCountMap.get(counterType) + 1);
                        break;
                    case 3:
                        selectionCountMap.put(counterType, selectionCountMap.get(counterType) + 1);
                        break;
                    default:
                        log.info("default switch");
                }
            } catch (Exception e) {
                log.error(type, counterType);
                log.error("switch Exception occured: ", e);
            }

        }
        for(String type : representativeTypes) {

            String counterType = counterTypes.get(type);
            Integer typeCount = selectionCountMap.get(type);
            Integer counterTypeCount = selectionCountMap.get(counterType);

            Double ratio = Double.valueOf(typeCount) / Double.valueOf(typeCount + counterTypeCount);


            ratioMap.put(type, Math.round(ratio*100.0));
        }

        return ratioMap;
    }

    private String getSurveyResult(List<TestItem> items, String expected) {

        String result = "";

        Integer[] weights = {3,2,1,-2,-3};
        Map<String, Integer> scores = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();


        for(String type: types) {
            scores.put(type, 0);
            countMap.put(type, 0);
        }


        for(TestItem item: items) {
            MbtiQuestion question = questionRepository.findById(Integer.valueOf(item.getSeq())).orElseThrow();
            String questionType = question.getType();
            String type = whichType(questionType);
            String counterType = counterTypes.get(questionType);


            Integer index = 0;
            try {
                switch (Integer.valueOf(item.getChoice())) {
                    //index={0,1,2}은 해당 질문에 긍정으로 평가해 type, index={3,4}는 부정으로 평가해 counterType
                    case -3:
                        index = 0;
                        break;
                    case -1:
                        index = 1;
                        break;
                    case 0:
                        index = 2;
                        break;
                    case 1:
                        index = 3;
                        break;
                    case 3:
                        index = 4;
                        break;
                    default:
                        log.info("default switch");
                }
            }catch(Exception e){
                log.error(type, counterType);
                log.error("switch Exception occured: ",e);
            }

            Integer weight = weights[index];
            if(!type.split("")[0].equals(question.getType())) {
                weight = -weight;
            } else {
                countMap.put(type, countMap.get(type) + 1);
            }
            scores.put(type, scores.get(type)+weight);
        }

        for(int i=0;i<4;i++){
            result += scores.get(types[i]) > 0 ? types[i].split("")[0] : (scores.get(types[0]) == 0 ? expected.split("")[i] : types[i].split("")[1]);
        }


        return result;
    }

    private void insertQuestions() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,List<Question>> candidates;
        try {
            candidates = mapper.readValue(new File("./src/main/resources/MBTI_questions.json"), new TypeReference<Map<String, List<Question>>>() {
            });
        } catch(Exception e){
            log.error("{} occured",e.getMessage());
            return;
        }
        List<Question> questions = new ArrayList<Question>();

        for(String key: candidates.keySet()) questions.addAll(candidates.get(key));
        questionRepository.saveAll(questions.stream().map(q->MbtiQuestion.builder().type(q.getType()).content(q.getQuestion()).build()).collect(Collectors.toList()));

    }
}
