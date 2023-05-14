package YOUmI.domain.board.model.entity;


import YOUmI.domain.board.model.vo.BoardRolePK;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
}
