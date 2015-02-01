package com.libqa.domain;

import com.libqa.application.enums.SharedContentsTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yong on 15. 2. 1..
 */
@Entity
@Data
public class QaContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="qaId", nullable = false)
    private Integer qaId;

    @Column(name="wikiId", nullable = true)
    private Integer wikiId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="contents", nullable = false)
    private String contents;

    @Column(name="contentsMarkup", nullable = false)
    private String contentsMarkup;

    @Column(name="userId", nullable = false)
    private Integer userId;

    @Column(name="userNick", nullable = false)
    private String userNick;

    @Column(name="viewCount", nullable = false)
    private Integer viewCount;

    @Column(name="recommandCount", nullable = false)
    private Integer recommandCount;

    @Column(name="isDeleted", nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Column(name="isShared", nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isShared;

    @Enumerated(EnumType.STRING)
    @Column(name = "sharedContentsType", length = 20)
    private SharedContentsTypeEnum sharedContentsType;

    @Column(name="sharedResponseId", nullable = true)
    private Integer sharedResponseId;

    @Column(name="isReplyed", nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isReplyed;

    @Column(name="insertDate", nullable = false)
    private Date insertDate;

    @Column(name="updateDate", nullable = false)
    private Date updateDate;

    @Column(name="insertUserId", nullable = false)
    private Integer insertUserId;

    @Column(name="updateUserId", nullable = false)
    private Integer updateUserId;
}
