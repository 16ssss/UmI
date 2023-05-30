package YOUmI.domain.board.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardRoleAddRequestDTO {
    @NotNull
    @Schema(description = "추가할 권한", example = "ROLE_I")
    private String addRole;
}
