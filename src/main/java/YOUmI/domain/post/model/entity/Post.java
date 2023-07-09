package YOUmI.domain.post.model.entity;


import YOUmI.common.converter.BooleanToYNConverter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @Column(name = "post_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQUENCE")
    private Integer postNo;

    @Column(name = "board_no")
    private int boardNo;

    @Column(name = "user_Id")
    private String userId;

    @Column(name = "posted_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private java.util.Date postedDate;

    @Column(name = "post_hits")
    private int postHits;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_content")
    @Lob
    private String postContent;

    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private java.util.Date modifiedDate;

    @Column(name = "delete_yn")
    @Convert(converter = BooleanToYNConverter.class)
    private boolean deleteYn;

    @Column(name = "delete_date")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date deleteDate;

    @Column(name = "writer_ip")
    private String writerIp;

    @OneToMany(mappedBy = "post")
    private List<PostFile> postFileList = new ArrayList<>();

    // 파일 추가
    public boolean addPostFile(PostFile postFile) {
        postFile.setPost(this);
        return this.getPostFileList().add(postFile);
    }

}
