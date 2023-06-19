package YOUmI.domain.MBTI.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestItem {

    // 시퀀스
    private String seq;

    //문항 답변(-3, -1, 0, 1, 3)
    private String choice;

    //MBTI 타입(알파벳)
    private String itemType;

    //문제 긍정 평가
    private Boolean like;
    //문제 부정 평가
    private Boolean dislike;

}
