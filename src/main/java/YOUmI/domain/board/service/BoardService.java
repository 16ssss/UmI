package YOUmI.domain.board.service;


import YOUmI.domain.board.model.dto.BoardCreateRequestDTO;
import YOUmI.domain.board.model.dto.BoardUpdateRequestDTO;

public interface BoardService {
    // 게시판 생성
    int createBoard(BoardCreateRequestDTO requestDTO);
    // 게시판 삭제
    boolean deleteBoard(int boardNo);
    // 게시판 수정
    boolean updateBoard(int boardNo, BoardUpdateRequestDTO requestDTO);
}
