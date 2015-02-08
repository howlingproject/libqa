package com.libqa.domain;

import com.libqa.application.enums.WikiRevisionActionTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class WikiSnapShot implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer wikiBackId;

    @Column(nullable = false)
    private Integer spaceId;

    @Column(nullable = false)
    private Integer wikiId;

    @Column
    private Integer parentsId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column
    private Integer orderIdx;

    @Column
    private Integer depthIdx;

    @Column(columnDefinition = "Text", nullable = false)
    private String contentsMarkup;

    @Column(columnDefinition = "Text", nullable = false)
    private String contents;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isLock = false;

    @Column(nullable = false, length = 10)
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @OneToMany(mappedBy = "wiki", fetch = FetchType.LAZY)
    private Set<WikiFile> wikiFileList;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private WikiRevisionActionTypeEnum revisionActionType;
}
