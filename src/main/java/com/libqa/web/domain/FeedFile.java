package com.libqa.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libqa.application.enums.FileType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString
@Table(indexes = {@Index(columnList = "isDeleted")})
@EqualsAndHashCode(of = "feedFileId")
public class FeedFile {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedFileId;

    @Column(nullable = false)
    private String realName;

    @Column(nullable = false)
    private String savedName;

    @Column(nullable = false, length = 80)
    private String filePath;

    @Column(nullable = false)
    private Integer fileSize;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private FileType fileType;

    @Column
    private Integer downloadCount = 0;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date insertDate;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false)
    private Integer insertUserId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedThreadId", referencedColumnName = "feedThreadId")
    private FeedThread feedThread;

    public boolean isFileType() {
        return FileType.FILE == this.fileType;
    }

    public boolean isImageType() {
        return FileType.IMAGE == this.fileType;
    }
}
