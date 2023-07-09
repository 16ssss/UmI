package YOUmI.domain.post.model.entity;

import YOUmI.common.converter.BooleanToYNConverter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_POST_FILE")
@SequenceGenerator(
        name = "POST_FILE_SEQUENCE",
        sequenceName = "POST_FILE_NO_SEQ",
        initialValue = 1,
        allocationSize = 1

)
@EntityListeners(AuditingEntityListener.class)
public class PostFile {

    @Id
    @Column(name = "post_file_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_FILE_SEQUENCE")
    private Integer postFileNo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private Post post;

    @Column(name = "save_name")
    private String saveName;

    @Column(name = "mime_type")
    private String type;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private java.util.Date createdDate;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_modified_date")
    @LastModifiedDate
    private java.util.Date fileModifiedDate;

    @Column(name = "delete_yn")
    @Convert(converter = BooleanToYNConverter.class)
    private boolean deleteYn;

    @Column(name = "delete_date")
    private java.util.Date deleteDate;
}
