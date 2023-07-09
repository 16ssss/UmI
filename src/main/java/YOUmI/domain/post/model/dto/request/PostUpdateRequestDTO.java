package YOUmI.domain.post.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostUpdateRequestDTO {
    private String postTitle;
    private String postContent;
}
