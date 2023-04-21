package YOUmI.domain.MBTI.repository;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import YOUmI.domain.MBTI.model.dto.Question;
import YOUmI.domain.MBTI.model.dto.TestResult;

public class QuestionRepositoryFromJson {

    // @Override
    // public List<Question> getQuestions() throws Exception{
    //     List<Question> questions = new ArrayList<>();
    //     ObjectMapper mapper = new ObjectMapper();
    //     Map<String,List<Question>> candidates = mapper.readValue(new File("./src/main/resources/MBTI_questions.json"), new TypeReference<Map<String,List<Question>>>() {});

    //     for(String type : candidates.keySet()) {
    //         Set<Integer> set = new HashSet<>();
    //         List<Question> list = candidates.get(type);
    //         int size = list.size();
    //         while(set.size() < QuestionRepository.COUNT_PER_TYPE) {
    //             SecureRandom random = SecureRandom.getInstanceStrong();
    //             set.add(random.nextInt(size));
    //         }
    //         Iterator<Integer> iter = set.iterator();
    //         while(iter.hasNext()){
    //             questions.add(list.get(iter.next()));
    //         }
    //         System.out.println(questions);
    //         System.out.println("\n");
    //     }
    //     return questions;
    // }

    // @Override
    // public void saveResult(String id, TestResult result) throws Exception {
    //     Map<String,Object> resultMap = new HashMap<String,Object>();
        
    //     ObjectMapper mapper = new ObjectMapper();

    //     resultMap.put("id", id);
    //     resultMap.put("result",result);

    //     mapper.writeValue(new File("./src/main/resources/test/result/"+id+"_result.json"), resultMap);
        
    // }
    
}
