package com.libqa.web.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by songanji on 2015. 2. 8..
 */
@Entity
@Data
public class WikiFile{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fileId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(nullable = false, length = 255)
    private String realName;

    @Column(nullable = false, length = 255)
    private String savedName;

    @Column(nullable = false, length = 80)
    private String filePath;

    @Column(nullable = false)
    private Integer fileSize;

    @Column(nullable = true, length = 10)
    private String fileType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Column(nullable = false)
    private Integer userId;

    private Integer wikiId;
}
