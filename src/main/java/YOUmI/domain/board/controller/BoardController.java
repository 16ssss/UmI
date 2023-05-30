package YOUmI.domain.board.controller;

import YOUmI.common.dto.ResponseDTO;
import YOUmI.domain.board.model.dto.request.*;
import YOUmI.domain.board.model.dto.response.BoardGetResponseDTO;
import YOUmI.domain.board.service.BoardService;
import YOUmI.util.enumeration.FAILURE;
import YOUmI.util.enumeration.SUCCESS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
@Tag(name = "게시판", description = "게시판 관련 COMMAND API")
public class BoardController {
    private final BoardService boardService;

    @GetMapping(value = "")
    @Operation(summary = "게시판 조회", description = "일반 사용자는 권한에 맞는 게시판 리스트을 조회할 수 있습니다.", method = "GET")
    public ResponseEntity<ResponseDTO> getBoardList(
            @PageableDefault(size = 1, page = 0) Pageable pageable
            , PagedResourcesAssembler<BoardGetResponseDTO> assembler
    ) {
        // dto로 변환
        PagingDTO pagingDTO = PagingDTO.builder()
                .pageable(pageable)
                .assembler(assembler)
                .build();

        // 서비스 호출
        PagedModel<EntityModel<BoardGetResponseDTO>> boardList = boardService.getBoardList(pagingDTO);

        // 응답
        ResponseDTO responseDTO = ResponseDTO.builder()
                .result(SUCCESS.SUCCESS)
                .resultObject(boardList)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    @ApiOperation(value = "", notes = "게시판을 등록합니다.")
    @Operation(summary = "게시판 생성", description = "어드민은 게시판을 생성할 수 있습니다.", method = "POST")
    public ResponseEntity<ResponseDTO> createBoard(@Validated @RequestBody BoardCreateRequestDTO requestDTO) {
        // 서비스 호출
        int createdNo = boardService.createBoard(requestDTO);

        // 에러 응답
        if (createdNo == -Integer.parseInt(FAILURE.DUPLICATE_RESOURCE.getCode())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseDTO.builder().result(FAILURE.DUPLICATE_RESOURCE).build());
        } else if (createdNo < 0) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder().result(FAILURE.FAILURE).build());
        }

        // 응답
        ResponseDTO responseDTO = ResponseDTO.builder()
                .result(SUCCESS.SUCCESS)
                .resultObject(createdNo)
                .build();
        return ResponseEntity.ok().body(responseDTO); // created 코드로 변경해야함
    }

    @PutMapping(value = "/{boardNo}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "게시판 수정", description = "어드민은 게시판을 수정할 수 있습니다.", method = "PUT")
    public ResponseEntity<ResponseDTO> updateBoard(@PathVariable int boardNo, @Validated @RequestBody BoardUpdateRequestDTO requestDTO) {
        // 서비스 호출
        boolean result = boardService.updateBoard(boardNo, requestDTO);

        // 응답
        ResponseDTO responseDTO = ResponseDTO.builder()
                .result(SUCCESS.SUCCESS)
                .resultObject(null)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping(value = "/{boardNo}/authority")
    @Operation(summary = "게시판 권한 제거", description = "어드민은 게시판의 권한을 제거할수 있습니다.", method = "DELETE")
    public ResponseEntity<ResponseDTO> removeBoardAuth(@PathVariable int boardNo, @Validated @RequestBody BoardRoleDeleteRequestDTO requestDTO){
        // 서비스 호출
        boolean result = boardService.deleteBoardRole(boardNo, requestDTO);

        if(!result){
            return ResponseEntity.badRequest().body(ResponseDTO.builder().result(FAILURE.FAILURE).build());
        }

        // 응답
        ResponseDTO responseDTO = ResponseDTO.builder()
                .result(SUCCESS.SUCCESS)
                .resultObject(null)
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

//    @PostMapping(value = "/{boardNo}/authority")
//    @Operation(summary = "게시판 권한 수정", description = "어드민은 게시판의 권한을 추가할 수 있습니다.", method = "POST")
//    public ResponseEntity<ResponseDTO> removeBoardAuth(@PathVariable int boardNo, @Validated @RequestBody BoardRoleAddRequestDTO requestDTO) {
//        // 서비스 호출
//        boolean result = boardService.addBoardRole(boardNo, requestDTO);
//        return
//    }

}
