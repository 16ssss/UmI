package YOUmI.domain.board.repository;

import YOUmI.domain.board.model.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Optional<Board> findByBoardNameAndDeletedYN(String boardName, boolean deletedYN);

    Optional<Board> findByBoardNoAndDeletedYN(int boardNo, boolean deletedYN);

    /**
     * ======== 조회 ========
     */
    @Query("select b from Board b join fetch BoardRole br on b.boardNo = br.boardRollPK.board.boardNo where br.boardRollPK.memberRole in :memberRoles and b.deletedYN = :deletedYN")
    Page<Board> findAllByMemberRole(Pageable pageable, @Param("memberRoles") List<String> memberRole, @Param("deletedYN") boolean deletedYN);


}
