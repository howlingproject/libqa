package com.libqa.web.view;

import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import lombok.Getter;

@Getter
public class DisplayFeedReply {
    private Integer feedReplyId;
    private String userNick;
    private String userImage;
    private String insertDate;
    private String feedReplyContent;
    private DisplayFeedAction likeFeedAction;
    private DisplayFeedAction claimFeedAction;

    public DisplayFeedReply(FeedReply feedReply) {
        this.feedReplyId = feedReply.getFeedReplyId();
        this.userNick = feedReply.getUserNick();
        this.insertDate = DisplayDateParser.parseForFeed(feedReply.getInsertDate());
        this.feedReplyContent = feedReply.getFeedReplyContent();
        this.likeFeedAction = createNotYetFeedAction();
        this.claimFeedAction = createNotYetFeedAction();
    }

    public DisplayFeedReply(FeedReply feedReply, User user, DisplayFeedAction likeFeedAction,
                            DisplayFeedAction claimFeedAction) {
        this.feedReplyId = feedReply.getFeedReplyId();
        this.userNick = feedReply.getUserNick();
        this.userImage = user.getUserImage();
        this.insertDate = DisplayDateParser.parseForFeed(feedReply.getInsertDate());
        this.feedReplyContent = feedReply.getFeedReplyContent();
        this.likeFeedAction = likeFeedAction;
        this.claimFeedAction = claimFeedAction;
    }

    private DisplayFeedAction createNotYetFeedAction() {
        return new DisplayFeedAction(0, false);
    }
}
