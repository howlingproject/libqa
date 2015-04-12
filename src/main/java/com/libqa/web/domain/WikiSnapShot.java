package com.libqa.web.domain;

import com.libqa.application.enums.WikiRevisionActionTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by songanji on 2015. 2. 8..
 */
@Entity
@Data
public class WikiSnapShot{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer wikiBackId;

    @ManyToOne
    @JoinColumn(name="wikiId", referencedColumnName = "wikiId", nullable=false)
    private Wiki wiki;

    @Column(nullable = false)
    private Integer spaceId;

    @Column(nullable = false)
    private Integer parentsId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer orderIdx;

    @Column(nullable = false, columnDefinition = "int default 0")
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

    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private WikiRevisionActionTypeEnum revisionActionType;
}
