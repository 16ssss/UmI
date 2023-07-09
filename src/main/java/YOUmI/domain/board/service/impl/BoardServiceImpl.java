package YOUmI.domain.board.service.impl;

import YOUmI.domain.board.model.dto.request.*;
import YOUmI.domain.board.model.dto.response.BoardGetResponseDTO;
import YOUmI.domain.board.model.entity.Board;
import YOUmI.domain.board.repository.BoardRepository;
import YOUmI.domain.board.service.BoardService;
import YOUmI.util.enumeration.FAILURE;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    /**
     * @param pagingDTO 페이징 처리를 위한 파라미터
     * @return 페이징 처리된 Board
     * todo: 권한에 따라 접근 가능한 게시판만 조회 가능하도록 변경해야 함.
     */
    @Override
    public PagedModel<EntityModel<BoardGetResponseDTO>> getBoardList(PagingDTO pagingDTO) {
        // 회원의 권한 가져오기 (민우님에게 받아오기)
        List<String> memberRoleList = new ArrayList<>();
        memberRoleList.add("ROLE_I");

        Pageable pageable = pagingDTO.getPageable();
        PagedResourcesAssembler<BoardGetResponseDTO> assembler = pagingDTO.getAssembler();

        // 사용자가 접근할 수 있는 게시판 목록 조회
        Page<BoardGetResponseDTO> pageBoard = boardRepository.findAllByMemberRole(pageable, memberRoleList, false);

        return assembler.toModel(pageBoard);
    }

    /**
     * 게시판 권한 제거
     *
     * @param boardNo    수정할 게시판 번호
     * @param requestDTO 제거할 권한 (게시판에 있는것만 가능)
     * @return boolean 성공시 true, 실패시 false
     */
    @Override
    @Transactional
    public boolean deleteBoardRole(int boardNo, BoardRoleDeleteRequestDTO requestDTO) {

        Optional<Board> foundBoardOpt = boardRepository.findByBoardNoAndDeletedYN(boardNo, false);
        if (foundBoardOpt.isEmpty()) {
            // 보드가 존재하지 않으므로 오류
            return false;
        }
        Board foundBoard = foundBoardOpt.get();
        String deleteRole = requestDTO.getDeleteRole();
        // 제거
        return foundBoard.removeBoardRole(deleteRole);
    }

    /**
     * 게시판 권한 추가
     *
     * @param boardNo    수정할 게시판 번호
     * @param requestDTO 추가할 권한
     * @return boolean 성공시 true, 실패시 false
     */
    @Transactional
    @Override
    public boolean addBoardRole(int boardNo, BoardRoleAddRequestDTO requestDTO) {

        Optional<Board> foundBoardOpt = boardRepository.findByBoardNoAndDeletedYN(boardNo, false);
        if (foundBoardOpt.isEmpty()) {
            // 보드가 존재하지 않으므로 오류
            return false;
        }
        Board foundBoard = foundBoardOpt.get();
        // 권한 목록이 게시판에 있는지 확인
        String addRole = requestDTO.getAddRole();
        /* 권한이 실제로 있는 권한인지 확인해야함 */

        // 모두 가능하다면 추가
        return foundBoard.addBoardRole(addRole);
    }

    @Override
    @Transactional
    public int createBoard(BoardCreateRequestDTO requestDTO) {
        // application - service 단에서는 단순히 도메인 조직의 순서만 관리해야 한다.
        // 단순히 엔티티에서 처리할 수 있는 로직은 엔티티, 다른 엔티티와 협업이 필요한 경우 service를 통해서 협업한다.

        // 게시판 이름이 중복인지 확인해야한다.(추가필요)
        if (boardRepository.findByBoardNameAndDeletedYN(requestDTO.getBoardName(), false).isPresent()) {
            return -Integer.parseInt(FAILURE.DUPLICATE_RESOURCE.getCode());
        }

        // 게시판 생성
        Board newBoard = Board.builder()
                .boardName(requestDTO.getBoardName())
                .boardDescribed(requestDTO.getBoardDescribed())
                .createdDate(new Date())
                .modifiedDate(new Date())
                .deletedYN(false)
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
        Optional<Board> boardOptional = boardRepository.findByBoardNoAndDeletedYN(boardNo, false);
        // 게시판이 존재하는 지 확인
        if (boardOptional.isEmpty()) {
            return false;
        }
        Board updateBoard = boardOptional.get();
        // 게시판 이름 변경
        if (boardUpdateRequestDTO.getUpdatedBoardName() != null) {
            // 만약 게시판 이름이 중복되는 경우 불가능
            if (boardRepository.findByBoardNameAndDeletedYN(boardUpdateRequestDTO.getUpdatedBoardName(), false).isPresent()) {
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
