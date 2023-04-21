package YOUmI.domain.MBTI.model.dto.Statistics;

import YOUmI.domain.MBTI.model.dto.Question;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    //TODO
    //여기 구조를 어떻게 짜야할지 고민

    private Question question;

    private Map<Integer, Integer> count;
}
