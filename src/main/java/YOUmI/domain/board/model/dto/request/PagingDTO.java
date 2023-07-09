package YOUmI.domain.board.model.dto.request;

import YOUmI.domain.board.model.dto.response.BoardGetResponseDTO;
import YOUmI.domain.board.model.entity.BoardRole;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PagingDTO {
    Pageable pageable;
    PagedResourcesAssembler<BoardGetResponseDTO> assembler;
}
