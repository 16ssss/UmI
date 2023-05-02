package YOUmI.domain.board.service.impl;

import YOUmI.domain.board.model.dto.BoardCreateRequestDTO;
import YOUmI.domain.board.model.dto.BoardUpdateRequestDTO;
import YOUmI.domain.board.model.entity.Board;
import YOUmI.domain.board.repository.BoardRepository;
import YOUmI.domain.board.service.BoardService;
import YOUmI.util.enumeration.FAILURE;
import YOUmI.util.enumeration.YN;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public int createBoard(BoardCreateRequestDTO requestDTO) {
        // application - service 단에서는 단순히 도메인 조직의 순서만 관리해야 한다.
        // 단순히 엔티티에서 처리할 수 있는 로직은 엔티티, 다른 엔티티와 협업이 필요한 경우 service를 통해서 협업한다.

        // 게시판 이름이 중복인지 확인해야한다.(추가필요)
        if (boardRepository.findByBoardNameAndDeletedYN(requestDTO.getBoardName(), YN.N.getYN()).isPresent()) {
            return -Integer.parseInt(FAILURE.DUPLICATE_RESOURCE.getCode());
        }

        // 게시판 생성
        Board newBoard = Board.builder()
                .boardName(requestDTO.getBoardName())
                .boardDescribed(requestDTO.getBoardDescribed())
                .createdDate(new Date())
                .modifiedDate(new Date())
                .deletedYN(YN.N.getYN())
                .build();

        try {
            // DB에 저장 시도
            boardRepository.save(newBoard);
        } catch (Exception e) {
            // 저장에 오류가 발생한다면
            log.error("insert failed, {}: {}", e.getClass(), e.getMessage());
            return -1;
        }
        // 저장에 성공한다면 추가된 boardNo 반환
        return newBoard.getBoardNo();
    }

    @Override
    public boolean deleteBoard(int boardNo) {
        return false;
    }

    @Override
    @Transactional
    public boolean updateBoard(int boardNo, BoardUpdateRequestDTO boardUpdateRequestDTO) {
        Optional<Board> boardOptional = boardRepository.findByBoardNoAndDeletedYN(boardNo, YN.N.getYN());
        // 게시판이 존재하는 지 확인
        if (boardOptional.isEmpty()) {
            return false;
        }
        Board updateBoard = boardOptional.get();
        // 게시판 이름 변경
        if (boardUpdateRequestDTO.getUpdatedBoardName() != null) {
            // 만약 게시판 이름이 중복되는 경우 불가능
            if (boardRepository.findByBoardNameAndDeletedYN(boardUpdateRequestDTO.getUpdatedBoardName(), YN.N.getYN()).isPresent()) {
                return false;
            }
            updateBoard.setBoardName(boardUpdateRequestDTO.getUpdatedBoardName());
        }
        // 게시판 설명 변경
        if (boardUpdateRequestDTO.getUpdatedBoardDescribed() != null) {
            updateBoard.setBoardDescribed(boardUpdateRequestDTO.getUpdatedBoardDescribed());
        }
        // 변경 일자 저장
        updateBoard.setModifiedDate(new Date());
        return true;
    }
}
