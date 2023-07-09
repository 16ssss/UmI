package YOUmI.domain.board.model.dto.response;

import YOUmI.domain.board.model.entity.Board;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardGetResponseDTO {
    int boardNo;
    String boardName;
    String boardDescribed;

    public static BoardGetResponseDTO of(Board board) {
        return BoardGetResponseDTO.builder()
                .boardNo(board.getBoardNo())
                .boardName(board.getBoardName())
                .boardDescribed(board.getBoardDescribed())
                .build();
    }
}
