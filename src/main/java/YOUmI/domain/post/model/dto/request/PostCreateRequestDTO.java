package YOUmI.domain.post.model.dto.request;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostCreateRequestDTO {
    @NotNull
    private int boardNo;
    @NotBlank
    private String postTitle;
    @NotBlank
    private String postContent;
    private List<MultipartFile> images;

}
