package YOUmI.domain.post.service;

import YOUmI.domain.post.model.entity.PostFile;
import org.springframework.web.multipart.MultipartFile;

public interface PostFileService {
    PostFile saveFile(MultipartFile multipartFile);
}
