package com.libqa.web.view.feed;

import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DisplayFeedReply {
    private Integer feedReplyId;
    private String userNick;
    private String userImage;
    private String insertDate;
    private String feedReplyContent;
    private Boolean isWriter;

    @Setter
    private DisplayFeedAction likeFeedAction;
    @Setter
    private DisplayFeedAction claimFeedAction;

    public DisplayFeedReply(FeedReply feedReply) {
        this.feedReplyId = feedReply.getFeedReplyId();
        this.userNick = feedReply.getUserNick();
        this.insertDate = DisplayDate.parse(feedReply.getInsertDate());
        this.feedReplyContent = feedReply.getFeedReplyContent();
        this.likeFeedAction = createNotYetFeedAction();
        this.claimFeedAction = createNotYetFeedAction();
    }

    DisplayFeedReply(FeedReply feedReply, User user, Boolean isWriter) {
        this.feedReplyId = feedReply.getFeedReplyId();
        this.userNick = feedReply.getUserNick();
        this.userImage = user.getUserImage();
        this.insertDate = DisplayDate.parse(feedReply.getInsertDate());
        this.feedReplyContent = feedReply.getFeedReplyContent();
        this.isWriter = isWriter;
    }

    private DisplayFeedAction createNotYetFeedAction() {
        return new DisplayFeedAction(false, 0);
    }
}
