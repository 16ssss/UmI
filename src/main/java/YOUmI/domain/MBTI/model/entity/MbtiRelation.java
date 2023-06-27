package YOUmI.domain.MBTI.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MbtiRelation {

    //좋은 궁합
    private List<String> good;

    //안좋은 궁합
    private List<String> bad;

}
