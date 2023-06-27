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

import YOUmI.domain.MBTI.model.entity.MbtiQuestion;
import YOUmI.domain.MBTI.model.entity.MbtiRelation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import YOUmI.domain.MBTI.model.dto.Question;
import YOUmI.domain.MBTI.model.dto.TestResult;

@Slf4j
@Repository
public class QuestionRepositoryFromJson {

     public MbtiRelation getMbtiRelation(String mbti){
         //List<Question> questions = new ArrayList<>();
         ObjectMapper mapper = new ObjectMapper();
         Map<String, MbtiRelation> relations = null;
         try {
             mapper.readValue(new File("./src/main/resources/MBTI_relations.json"), new TypeReference<Map<String, MbtiRelation>>() {});
         } catch(Exception e){
             log.error("Exception occured on getMbtiRelation\n",e);

         }

         return relations.get(mbti) != null ? relations.get(mbti) : null;
     }

    // @Override
    // public void saveResult(String id, TestResult result) throws Exception {
    //     Map<String,Object> resultMap = new HashMap<String,Object>();
        
    //     ObjectMapper mapper = new ObjectMapper();

    //     resultMap.put("id", id);
    //     resultMap.put("result",result);

    //     mapper.writeValue(new File("./src/main/resources/test/result/"+id+"_result.json"), resultMap);
        
    // }
    
}
