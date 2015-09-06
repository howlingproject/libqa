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
    private DisplayFeedAction likeFeedAction;
    private DisplayFeedAction claimFeedAction;

    public DisplayFeedReply(Integer feedId, FeedReply feedReply, DisplayFeedAction likeFeedAction, DisplayFeedAction claimFeedAction) {
        this.feedId = feedId;
        this.feedReplyId = feedReply.getFeedReplyId();
        this.userNick = feedReply.getUserNick();
        this.insertDate = feedReply.getInsertDate();
        this.likeFeedAction = likeFeedAction;
        this.claimFeedAction = claimFeedAction;
        this.feedReplyContent = feedReply.getFeedReplyContent();
    }
}
