package YOUmI.domain.board.model.vo;

import YOUmI.domain.board.model.entity.Board;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardRolePK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_NO")
    @ToString.Exclude
    private Board board;

    @Column(name = "MEMBER_ROLE")
    private String memberRole;

    @Override
    public int hashCode() {
        return Objects.hash(board.hashCode(), memberRole);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BoardRolePK productLikesMemberPK = (BoardRolePK) obj;
        return board == productLikesMemberPK.getBoard() && Objects.equals(memberRole, productLikesMemberPK.getMemberRole());
    }
}
