package YOUmI.domain.MBTI.model.dto;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionEvaluation {

    //문제번호
    private Integer questionSeq;

    //문제 긍정 평가
    private Boolean like;
    //문제 부정 평가
    private Boolean dislike;

}
