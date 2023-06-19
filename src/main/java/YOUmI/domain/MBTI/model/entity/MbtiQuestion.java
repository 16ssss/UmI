package YOUmI.domain.MBTI.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MbtiQuestion {

    @Id
    private Integer seq;

    private String type;

    private String content;

    private String leftPhrase;

    private String leftImg;

    private String rightPhrase;

    private String rightImg;

    @Column(name="`like`")
    private Integer like;

    @Column(name="`dislike`")
    private Integer dislike;

}
