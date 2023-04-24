package YOUmI.domain.board.repository;

import YOUmI.domain.board.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Optional<Board> findByBoardNameAndDeletedYN(String boardName, char deletedYN);
}
