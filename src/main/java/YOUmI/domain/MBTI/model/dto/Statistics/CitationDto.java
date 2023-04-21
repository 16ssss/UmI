package YOUmI.domain.MBTI.model.dto.Statistics;

import YOUmI.domain.MBTI.model.dto.Question;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitationDto {

    private Question question;

    private Long count;

}
