package YOUmI.domain.MBTI.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResult {
    
    private List<TestItem> items;

    // 프론트에서 사용자 입력
    private String username;

    private String expectedResult;

    private String comment;

    //어떤 타입, 몇 번 문제, 몇점

}
