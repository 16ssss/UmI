package YOUmI.domain.MBTI.repository;

import YOUmI.domain.MBTI.model.entity.MbtiRelation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Map;

@Slf4j
@Repository
public class QuestionRepositoryFromJson {

    @Value("${json.path}")
    private String path;


    public MbtiRelation getMbtiRelation(String mbti) {
        //List<Question> questions = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, MbtiRelation> relations = null;
        try {
            relations = mapper.readValue(new File(path), new TypeReference<Map<String, MbtiRelation>>() {
            });
        } catch (Exception e) {
            log.error("Exception occured on getMbtiRelation\n", e);

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
