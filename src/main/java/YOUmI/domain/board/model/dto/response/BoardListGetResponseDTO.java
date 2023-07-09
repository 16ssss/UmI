package YOUmI.domain.board.model.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardListGetResponseDTO {
    List<BoardGetResponseDTO> boardList;
    int totalPage;
    int nowPage;
}
