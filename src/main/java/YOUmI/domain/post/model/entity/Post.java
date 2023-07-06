package YOUmI.domain.post.model.entity;


import YOUmI.common.converter.BooleanToYNConverter;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_POST")
@SequenceGenerator(
        name = "POST_SEQUENCE",
        sequenceName = "POST_NO_SEQ",
        initialValue = 1,
        allocationSize = 1

)
public class Post {

    @Id
    @Column(name = "post_no")
    private Integer postNo;

    @Column(name ="board_no")
    private int boardNo;

    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "posted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date postedDate;

    @Column(name = "post_hits")
    private int postHits;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date modifiedDate;

    @Column(name = "delete_yn")
    @Convert(converter = BooleanToYNConverter.class)
    private boolean deleteYn;

    @Column(name = "delete_date")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date deleteDate;

    @Column(name = "writer_ip")
    private String writerIp;

}
