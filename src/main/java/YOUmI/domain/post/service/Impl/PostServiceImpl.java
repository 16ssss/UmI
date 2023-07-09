package YOUmI.domain.post.service.Impl;

import YOUmI.domain.post.model.dto.request.PostCreateRequestDTO;
import YOUmI.domain.post.model.dto.request.PostUpdateRequestDTO;
import YOUmI.domain.post.model.entity.Post;
import YOUmI.domain.post.model.entity.PostFile;
import YOUmI.domain.post.respository.PostRepository;
import YOUmI.domain.post.service.PostFileService;
import YOUmI.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostFileService postFileService;

    @Override
    @Transactional
    public int createPost(String id, String remoteAddr, PostCreateRequestDTO requestDTO) {

        // 해당 게시판에 권한이 있는지 확인해야함.

        // 게시글 저장
        Post newPost = Post.builder()
                .boardNo(requestDTO.getBoardNo())
                .userId(id)
                .postTitle(requestDTO.getPostTitle())
                .postContent(requestDTO.getPostContent())
                .writerIp(remoteAddr)
                .postHits(0)
                .deleteYn(false)
                .postFileList(new ArrayList<>())
                .build();

        // 이미지 저장
        if(requestDTO.getImages() != null) {
            for (MultipartFile file : requestDTO.getImages()) {
                // 파일 생성
                PostFile postFile = postFileService.saveFile(file);
                newPost.addPostFile(postFile);
            }
        }

        return postRepository.save(newPost).getPostNo();
    }

    @Override
    public boolean updatePost(int postNo, String id, PostUpdateRequestDTO requestDTO) {
        Optional<Post> firstByPostNo = postRepository.findFirstByPostNo(postNo);
        if (firstByPostNo.isEmpty()) {
            log.info(postNo + " 게시글이 존재하지 않습니다.");
            return false;
        }
        Post post = firstByPostNo.get();
        if (!id.equals(post.getUserId())) {
            log.info("게시글 작성자가 아닙니다. :" + id);
            return false;
        }

        if (!requestDTO.getPostTitle().isEmpty()) {
            post.setPostTitle(requestDTO.getPostTitle());
        }
        if (!requestDTO.getPostContent().isEmpty()) {
            post.setPostContent(requestDTO.getPostContent());
        }

        return true;
    }


}
