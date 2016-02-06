package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.ThreadType;
import com.libqa.web.domain.User;

public class FeedReplyClaim extends FeedActionActor {
    private FeedReplyClaim(Integer feedReplyId, User actionUser) {
        super(feedReplyId, actionUser);
    }

    public static FeedReplyClaim of(Integer feedReplyId, User actionUser) {
        return new FeedReplyClaim(feedReplyId, actionUser);
    }

    @Override
    public ThreadType getThreadType() {
        return ThreadType.FEED_REPLY;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.CLAIM;
    }

}
