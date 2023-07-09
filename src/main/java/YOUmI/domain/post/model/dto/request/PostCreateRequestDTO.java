package YOUmI.domain.post.model.dto.request;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostCreateRequestDTO {
    private int boardNo;
    private String postTitle;
    private String postContent;
    private List<MultipartFile> images;

}
