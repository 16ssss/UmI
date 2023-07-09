package YOUmI.domain.board.model.entity;


import YOUmI.domain.board.model.vo.BoardRolePK;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter()
@Getter()
@ToString
@Entity
@Table(name = "TBL_BOARD_ROLE")
public class BoardRole {

    @EmbeddedId
    private BoardRolePK boardRollPK;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardRole boardRole = (BoardRole) o;
        return Objects.equals(boardRollPK, boardRole.boardRollPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardRollPK);
    }
}
