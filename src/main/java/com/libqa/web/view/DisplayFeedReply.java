package com.libqa.web.view;

import com.libqa.web.domain.FeedReply;
import lombok.Getter;

@Getter
public class DisplayFeedReply {
    private final long feedReplyId;
    private String userNick;
    private String insertDate;
    private String feedReplyContent;
    private DisplayFeedAction likeFeedAction;
    private DisplayFeedAction claimFeedAction;

    public DisplayFeedReply(FeedReply feedReply) {
        this.feedReplyId = feedReply.getFeedReplyId();
        this.userNick = feedReply.getUserNick();
        this.insertDate = DisplayFeedBuilder.toDisplayDate(feedReply.getInsertDate());
        this.feedReplyContent = feedReply.getFeedReplyContent();
        this.likeFeedAction = new DisplayFeedAction(0, false);
        this.claimFeedAction = new DisplayFeedAction(0, false);
    }

    public DisplayFeedReply(FeedReply feedReply, DisplayFeedAction likeFeedAction, DisplayFeedAction claimFeedAction) {
        this.feedReplyId = feedReply.getFeedReplyId();
        this.userNick = feedReply.getUserNick();
        this.insertDate = DisplayFeedBuilder.toDisplayDate(feedReply.getInsertDate());
        this.feedReplyContent = feedReply.getFeedReplyContent();
        this.likeFeedAction = likeFeedAction;
        this.claimFeedAction = claimFeedAction;
    }
}
