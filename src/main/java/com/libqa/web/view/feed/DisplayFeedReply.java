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
    private Boolean isReplyWriter;

    @Setter
    private DisplayFeedAction likeFeedAction;
    @Setter
    private DisplayFeedAction claimFeedAction;

    DisplayFeedReply(FeedReply feedReply, User user, Boolean isReplyWriter) {
        this.feedReplyId = feedReply.getFeedReplyId();
        this.userNick = feedReply.getUserNick();
        this.userImage = user.getUserImage();
        this.insertDate = DisplayDate.parse(feedReply.getInsertDate());
        this.feedReplyContent = feedReply.getFeedReplyContent();
        this.isReplyWriter = isReplyWriter;
    }

}
