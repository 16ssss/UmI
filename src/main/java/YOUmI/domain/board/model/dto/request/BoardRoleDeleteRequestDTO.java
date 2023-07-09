package YOUmI.domain.board.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class BoardRoleDeleteRequestDTO {

    @NotNull
    @Schema(description = "제거할 권한 목록", example = "ROLE_I")
    private String deleteRole;

}
