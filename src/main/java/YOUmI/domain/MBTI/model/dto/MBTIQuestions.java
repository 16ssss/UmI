package YOUmI.domain.MBTI.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MBTIQuestions {
    
    private List<Question> questions;

    private Integer id;

}
