package YOUmI.domain.post.model.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostUpdateRequestDTO {
    @NotNull
    private String postTitle;
    @NotBlank
    private String postContent;
}
