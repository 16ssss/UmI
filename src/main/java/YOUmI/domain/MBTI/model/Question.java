package YOUmI.domain.MBTI.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {
    
    private String type;

    private String question;

    private Integer seq;

    private String leftPhrase;

    private String leftImg;

    private String rightPhrase;

    private String rightImg;

    private Integer like;

    private Integer dislike;

    @Override
    public String toString(){
        return "type: "+type+", question: "+question;
    }

}
