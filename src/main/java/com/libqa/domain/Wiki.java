package com.libqa.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

/**
 * Created by songanji on 2015. 2. 8..
 */
@Entity
@Data
@Slf4j
public class Wiki {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer wikiId;

    @Column(nullable = false)
    private Integer spaceId;

    @Column
    private Integer parentsId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "int default 0")
    private Integer orderIdx;

    @Column(columnDefinition = "int default 0")
    private Integer depthIdx;

    @Column(columnDefinition = "Text", nullable = false)
    private String contents;

    @Column(columnDefinition = "Text", nullable = false)
    private String contentsMarkup;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isLock = false;

    @Column(nullable = false, length = 20)
    private String passwd;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false)
    private Integer userId;

    @Column
    private Integer viewCount;

    @Column
    private Integer likeCount;

    @Column
    private Integer reportCount;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isFixed = false;

    @Column(length = 100)
    private String wikiUrl;

    @Column(length = 16)
    private String currentIp;

    @Column(length = 10)
    private String editReason;

    @Column(length = 10)
    private String revision;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    boolean isDeleted = false;

    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @OneToMany(mappedBy = "wiki", fetch = FetchType.LAZY)
    private Set<WikiFile> wikiFileList;
}
