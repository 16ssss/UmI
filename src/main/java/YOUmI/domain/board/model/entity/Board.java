package YOUmI.domain.board.model.entity;


import YOUmI.common.converter.BooleanToYNConverter;
import YOUmI.domain.board.model.vo.BoardRolePK;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Entity
@Table(name = "TBL_BOARD")
@SequenceGenerator(
        name = "BOARD_SEQUENCE",
        sequenceName = "BOARD_NO_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQUENCE")
    @Column(name = "board_no", nullable = false)
    private Integer boardNo;

    @Column(name = "board_name", nullable = false, unique = true)
    private String boardName;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private java.util.Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", nullable = false)
    private java.util.Date modifiedDate;

    @Column(name = "board_described")
    private String boardDescribed;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "deleted_yn", nullable = false)
    private boolean deletedYN;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_date")
    private java.util.Date deletedDate;

    @OneToMany(mappedBy = "boardRollPK.board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardRole> boardRoleList = new ArrayList<>();


    /**
     * 권한 추가
     *
     * @param addRole 추가할 권한 명
     */
    public boolean addBoardRole(String addRole) {
        BoardRole boardRole = new BoardRole(new BoardRolePK(this, addRole));
        return this.getBoardRoleList().add(boardRole);
    }

    /**
     * 권한 제거
     *
     * @param removeRole 삭제할 권한 명
     */
    public boolean removeBoardRole(String removeRole) {
        BoardRole boardRole = new BoardRole(new BoardRolePK(this, removeRole));
        return this.getBoardRoleList().remove(boardRole);
    }
}
