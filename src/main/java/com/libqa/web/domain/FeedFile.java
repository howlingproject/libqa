package com.libqa.web.domain;

import com.libqa.application.enums.FileTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class FeedFile {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedFileId;

    @Column(nullable = false, length = 40)
    private String realName;

    @Column(nullable = false, length = 80)
    private String savedName;

    @Column(nullable = false, length = 80)
    private String filePath;

    @Column(nullable = false)
    private Integer fileSize;

    @Column(nullable = true, length = 10)
    @Enumerated(EnumType.STRING)
    private FileTypeEnum fileType;

    @Column
    private Integer downloadCount = 0;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;  // TODO index 추가 필요

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date insertDate;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false)
    private Integer insertUserId;

    private Long feedId;

    public boolean isFileType() {
        return FileTypeEnum.FILE == this.fileType;
    }

    public boolean isImageType() {
        return FileTypeEnum.IMAGE == this.fileType;
    }
}
