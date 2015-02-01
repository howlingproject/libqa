package com.libqa.domain;

import com.libqa.application.enums.SharedContentsTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "feed")
public class Feed {

    @Id
    @Column(name = "feedId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedId;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "userNick", nullable = false)
    private String userNick;

    @Column(name = "sharedResponseId")
    private Integer sharedResponseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "sharedContentsType")
    private SharedContentsTypeEnum sharedContentsType;

    @Column(name = "feedContent")
    private String feedContent;

    @Column(name = "feedUrl", length = 40)
    private String feedUrl;

    @Column(name = "likeCount")
    private Integer likeCount;

    @Column(name = "claimCount")
    private Integer claimCount;

    @Column(name = "isShared", columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isShared;

    @Column(name = "isPrivate", columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isPrivate;

    @Column(name = "isSharedFb", columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isSharedFb;

    @Column(name = "isSharedTw", columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isSharedTw;

    @Column(name = "isSharedGp", columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isSharedGp;

    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(name = "insertUserId", nullable = false)
    private Integer insertUserId;

    @Column(name = "updateUserId")
    private Integer updateUserId;
}
