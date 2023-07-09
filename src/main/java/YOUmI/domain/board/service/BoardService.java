package YOUmI.domain.board.service;


import YOUmI.domain.board.model.dto.request.*;
import YOUmI.domain.board.model.dto.response.BoardGetResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface BoardService {
    // 게시판 생성
    int createBoard(BoardCreateRequestDTO requestDTO);

    // 게시판 삭제
    boolean deleteBoard(int boardNo);

    // 게시판 수정
    boolean updateBoard(int boardNo, BoardUpdateRequestDTO requestDTO);

    // 게시판 조회
    PagedModel<EntityModel<BoardGetResponseDTO>> getBoardList(PagingDTO requestDTO);

    // 게시판 권한 제거
    boolean deleteBoardRole(int boardNo, BoardRoleDeleteRequestDTO requestDTO);

    // 게시판 권한 추가
    boolean addBoardRole(int boardNo, BoardRoleAddRequestDTO requestDTO);
}
