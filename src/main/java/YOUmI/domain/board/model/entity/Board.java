package YOUmI.domain.board.model.entity;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;


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

    @Column(name = "deleted_yn", nullable = false)
    private char deletedYN;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_date")
    private java.util.Date deletedDate;

}
