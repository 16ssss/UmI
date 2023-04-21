package YOUmI.domain.MBTI.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    
    private Integer seq;

    private String type;

    private String question;

    private String leftPhrase;

    private String leftImg;

    private String rightPhrase;

    private String rightImg;

    @Override
    public String toString(){
        return "type: "+type+", question: "+question+",leftPhrase: "+leftPhrase+", rightPhrase: "+rightPhrase;
    }

}
