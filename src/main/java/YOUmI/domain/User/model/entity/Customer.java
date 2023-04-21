package YOUmI.domain.User.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private String id;

    private String username;

    private String surveyComment;

    private String expectedMbti;

    private String mbti;

    private String password;
}
