package YOUmI.domain.board.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardUpdateRequestDTO {
    // 수정할 이름
    @Schema(description = "수정 후 게시판 이름", example = "I 게시판")
    @Size(min = 2, max = 20)
    private String updatedBoardName;
    // 수정할 게시판 설명
    @Schema(description = "수정 후 게시판 설명", example = "I만 접근할 수 있는 게시판")
    private String updatedBoardDescribed;

}
