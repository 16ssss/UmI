package YOUmI.domain.board.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardCreateRequestDTO {
    @Schema(description = "게시판 이름", example = "자유 게시판")
    private String boardName;
    @Schema(description = "게시판 설명", example = "자유 게시판입니다.")
    private String boardDescribed;
}
