package YOUmI.domain.MBTI.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MbtiSurveyResultId.class)
public class MbtiSurveyResult {

    @Id
    private String id;

    @Id
    private Integer seq;

    private Integer choice;

}
