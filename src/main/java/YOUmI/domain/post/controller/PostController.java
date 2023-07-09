package YOUmI.domain.post.controller;

import YOUmI.common.dto.ResponseDTO;
import YOUmI.domain.post.model.dto.request.PostCreateRequestDTO;
import YOUmI.domain.post.model.dto.request.PostUpdateRequestDTO;
import YOUmI.domain.post.service.PostService;
import YOUmI.util.enumeration.FAILURE;
import YOUmI.util.enumeration.SUCCESS;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(value = "")
    @Operation(summary = "게시글 생성", description = "일반 사용자는 권한에 맞는 게시판에서 게시글을 생성할 수 있습니다.", method = "POST")
    public ResponseEntity<ResponseDTO> createPost(@Valid PostCreateRequestDTO requestDTO, HttpServletRequest request) {

        if(requestDTO.getImages() != null) {
            // 파일이 이미지파일인지 확인 (PostFile에서 검사해야하는가? Post에서 검사해야하는가?)
            for (MultipartFile file : requestDTO.getImages()) {
                if (!file.getContentType().startsWith("image")) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDTO.builder().result(FAILURE.NOT_IMAGE_FILE).build());
                }
            }
        }

        /* 현재 접속한 사용자 조회가 들어가야 합니다. */
//        String id = principal.getName(); // 임시
        String id = "22";

        String remoteAddr = request.getRemoteAddr(); // 현재 접속한 IP 주소

        // 서비스 호출
        int newPostNo = postService.createPost(id, remoteAddr, requestDTO);

        // 응답 처리
        if (newPostNo < 0) return ResponseEntity.badRequest()
                .body(ResponseDTO.builder().result(FAILURE.ABSENT_REQUIRED_VALUE).build());

        ResponseDTO res = ResponseDTO.builder()
                .result(SUCCESS.SUCCESS)
                .resultObject(newPostNo)
                .build();

        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/{postNo}")
    @Operation(summary = "게시글 수정", description = "일반 사용자는 자신이 작성한 글을 수정할 수 있습니다.", method = "PUT")
    public ResponseEntity<ResponseDTO> updatePost(@PathVariable int postNo, @RequestBody @Valid PostUpdateRequestDTO requestDTO) {
        /* 현재 접속한 유저 ID 확인 */
        String id = "22"; // 임시

        /* 서비스 호출 */
        boolean result = postService.updatePost(postNo, id, requestDTO);

        /* 응답 */
        if (result) {
            /* 수정 성공 시 */
            return ResponseEntity.ok().body(
                    ResponseDTO.builder().result(SUCCESS.SUCCESS).build()
            );
        } else {
            /* 수정 실패 시 */
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder().result(FAILURE.FAILURE).build()
            );

        }
    }
}
