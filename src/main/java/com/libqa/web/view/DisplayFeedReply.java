package com.libqa.web.view;

import com.libqa.web.domain.FeedReply;
import lombok.Getter;

import java.util.Date;

@Getter
public class DisplayFeedReply {                       
    private final long feedId;
    private final long feedReplyId;
    private String feedReplyContent;
    private String userNick;
    private Date insertDate;
    private int likeCount;
    private int claimCount;                 

    public DisplayFeedReply(Integer feedId, FeedReply feedReply) {
        this.feedId = feedId;
        this.feedReplyId = feedReply.getFeedReplyId();
        this.userNick = feedReply.getUserNick();
        this.insertDate = feedReply.getInsertDate();
        this.likeCount = feedReply.getLikeCount();
        this.claimCount = feedReply.getClaimCount();
        this.feedReplyContent = feedReply.getFeedReplyContent();
    }
}
