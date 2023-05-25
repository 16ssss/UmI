package YOUmI.domain.MBTI.service.impl;

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


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CustomerRepository userRepository;

    @Autowired
    private SurveyResultRepository surveyResultRepository;

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
            log.info("\n");
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

        result.getQuestionEvaluations().stream().forEach(evaluation -> {
            MbtiQuestion mbtiQuestion = questionRepository.findById(evaluation.getQuestionSeq()).orElse(null);
            log.info(mbtiQuestion.toString());
            if(mbtiQuestion != null) {
                if (evaluation.getLike()) {
                    mbtiQuestion.setLike(mbtiQuestion.getLike() + 1);
                } else {
                    mbtiQuestion.setDislike(mbtiQuestion.getDislike() + 1);
                }
                log.info(mbtiQuestion.toString());

                questionRepository.save(mbtiQuestion);
            }
        });

        return user.getExpectedMbti();
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

        Map<String, Double> tmpResult = new HashMap<>();

        Map<String, Integer> totalCountMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for(String type : types) {

            countMap.put(type, 0);
        }

        for(String type : typeArray) {
            totalCountMap.put(type, 0);
        }

        log.info(totalCountMap.keySet().toString());

        for(TestItem item : items) {
            log.info("item seq: "+item.getSeq().toString());
            MbtiQuestion question = questionRepository.findById(Integer.valueOf(item.getSeq())).orElseThrow();
            String type = whichType(question.getType());
            log.info("question.getType(): "+question.getType());
            log.info("type:: "+type);
            countMap.put(type, (Integer)countMap.get(type)+1);

            totalCountMap.put(question.getType(), (Integer)totalCountMap.get(question.getType())+1);


        }

        for(String type : countMap.keySet()) {

            for(String t : typeArray) {
                if(type.contains(t)) {
                    log.error("contains :"+t+" , "+type);
                    tmpResult.put(t, Double.valueOf(totalCountMap.get(t))/Double.valueOf(countMap.get(type)));
                }
            }
        }
        log.info(countMap);
        log.info(totalCountMap);
        log.info(tmpResult);
        String[] representativeType = {"E","S","T","J"};

        Map<String, Long> result = new HashMap<>();

        for(String type : representativeType) {

            result.put(type, Math.round(tmpResult.get(type)*100.0));
        }

        return result;
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
            String type = whichType(question.getType());

            Integer index = 0;
            try {
                switch (Integer.valueOf(item.getChoice())) {
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
            result += scores.get(types[0]) > 0 ? types[i].split("")[0] : (scores.get(types[0]) == 0 ? expected.split("")[i] : types[i].split("")[1]);
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
