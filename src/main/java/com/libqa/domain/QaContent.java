package com.libqa.domain;

import com.libqa.application.enums.ContentsTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yong on 15. 2. 1..
 */
@Entity
@Data
public class QaContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer qaId;

    @Column(nullable = true)
    private Integer wikiId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition="Text")
    private String contents;

    @Column(nullable = false, columnDefinition="Text")
    private String contentsMarkup;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String userNick;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer viewCount;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer recommandCount;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isShared;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ContentsTypeEnum sharedContentsType;

    @Column(nullable = true)
    private Integer sharedResponseId;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isReplyed;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column(nullable = false)
    private Integer updateUserId;

    @OneToMany(mappedBy = "qaContent", fetch = FetchType.LAZY)
    private List<QaReply> qaReplys;

    @OneToMany(mappedBy = "qaContent", fetch = FetchType.LAZY)
    private List<QaRecommand> qaRecommands;
}
