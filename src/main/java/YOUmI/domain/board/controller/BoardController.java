package YOUmI.domain.board.controller;

import YOUmI.common.dto.ResponseDTO;
import YOUmI.domain.board.model.dto.BoardCreateRequestDTO;
import YOUmI.domain.board.model.dto.BoardUpdateRequestDTO;
import YOUmI.domain.board.service.BoardService;
import YOUmI.util.enumeration.FAILURE;
import YOUmI.util.enumeration.SUCCESS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
