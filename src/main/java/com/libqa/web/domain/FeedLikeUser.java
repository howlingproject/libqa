package com.libqa.web.domain;

import com.libqa.application.enums.FeedLikeTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString
@EqualsAndHashCode(of = "feedLikeUserId")
public class FeedLikeUser {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedLikeUserId;

    @Column(nullable = false)
    private Long replyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private FeedLikeTypeEnum feedLikeType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isCanceled;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedId", referencedColumnName = "feedId", nullable = false)
    private Feed feed;
    
    @Transient
    public static FeedLikeUser createNew(Feed feed, FeedLikeTypeEnum type) {
        FeedLikeUser feedLikeUser = new FeedLikeUser();
        feedLikeUser.setFeed(feed);
        feedLikeUser.setCanceled(true);
        feedLikeUser.setFeedLikeType(type);
        feedLikeUser.setReplyId(-1L);
        feedLikeUser.setUserId(feed.getUserId());
        feedLikeUser.setUserNick(feed.getUserNick());
        feedLikeUser.setInsertUserId(feed.getUserId());
        feedLikeUser.setUpdateUserId(feed.getUserId());
        return feedLikeUser;
    }
}
