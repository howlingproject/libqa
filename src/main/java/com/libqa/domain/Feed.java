package com.libqa.domain;

import com.libqa.application.enums.SharedContentsTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Feed {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedId;

    @Column
    private String sharedResponseId;

    @Column
    @Enumerated(EnumType.STRING)
    private SharedContentsTypeEnum sharedContentsType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String feedContent;

    @Column(length = 255)
    private String feedUrl;

    @Column
    private Integer likeCount;

    @Column
    private Integer claimCount;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isShared;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isPrivate;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isSharedFb;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isSharedTw;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isSharedGp;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column
    private Integer updateUserId;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY)
    private List<FeedReply> feedReplies;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY)
    private List<FeedFile> feedFiles;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY)
    private List<FeedLikeUser> feedLikeUsers;
}
