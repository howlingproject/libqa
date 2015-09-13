package com.libqa.web.domain;

import com.libqa.application.enums.SocialChannelType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yong on 15. 2. 1..
 */
@Data
@Entity
@EqualsAndHashCode
public class QaContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer qaId;

    @Column(nullable = true)
    private Integer wikiId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition="Text")
    private String contents;

    @Column(nullable = false, columnDefinition="Text")
    private String contentsMarkup;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false, columnDefinition = "int DEFAULT 0")
    private int viewCount = 0;

    @Column(nullable = false, columnDefinition = "int DEFAULT 0")
    private int recommendCount = 0;

    @Column(nullable = false, columnDefinition = "int DEFAULT 0")
    private int nonrecommendCount = 0;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isShared;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SocialChannelType sharedContentsType;

    @Column(nullable = true)
    private Integer sharedResponseId;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isReplyed;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column
    private Integer updateUserId;

    @OneToMany(mappedBy = "qaId", fetch = FetchType.LAZY)
    private List<QaReply> qaReplys;

    @OneToMany(mappedBy = "qaId", fetch = FetchType.LAZY)
    private List<QaRecommend> qaRecommends;

    @OneToMany(mappedBy = "qaId", fetch = FetchType.LAZY)
    private List<QaFile> qaFiles;

}
