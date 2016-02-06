package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.PostType;
import com.libqa.web.domain.User;

public class FeedReplyLike extends FeedActionActor {
    private FeedReplyLike(Integer feedReplyId, User actionUser) {
        super(feedReplyId, actionUser);
    }

    public static FeedReplyLike of(Integer feedReplyId, User actionUser) {
        return new FeedReplyLike(feedReplyId, actionUser);
    }

    @Override
    public PostType getPostType() {
        return PostType.REPLY;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.LIKE;
    }
}
