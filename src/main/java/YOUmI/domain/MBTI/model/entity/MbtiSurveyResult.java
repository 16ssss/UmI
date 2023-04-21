package YOUmI.domain.MBTI.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MbtiSurveyResult {

    @Id
    private String id;

    private Integer seq;

    private Integer choice;

}
