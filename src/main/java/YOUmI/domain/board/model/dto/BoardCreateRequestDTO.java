package YOUmI.domain.board.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardCreateRequestDTO {
    @NotBlank
    @Size(min = 2, max = 20)
    @Schema(description = "게시판 이름", example = "자유 게시판")
    private String boardName;
    @Schema(description = "게시판 설명", example = "누구나 접근할 수 있는 게시판")
    private String boardDescribed;
}
