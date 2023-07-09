package YOUmI.domain.post.service;

import YOUmI.domain.post.model.dto.request.PostCreateRequestDTO;
import YOUmI.domain.post.model.dto.request.PostUpdateRequestDTO;

public interface PostService {

    int createPost(String id, String remoteAddr, PostCreateRequestDTO requestDTO);

    boolean updatePost(int postNo, String id, PostUpdateRequestDTO requestDTO);

}
