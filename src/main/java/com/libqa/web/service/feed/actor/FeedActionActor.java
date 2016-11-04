package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.PostType;
import com.libqa.web.domain.User;
import lombok.Getter;

public abstract class FeedActionActor {
    @Getter
    private Integer feedActorId;
    @Getter
    private User actionUser;

    /**
     * @param feedActorId 각 actor 의 unique key 를 나타낸다. <br />
     *                    feed => feedThreadId, feedReply => feedReplyId
     * @param actionUser  action 을 취한 user
     */
    FeedActionActor(Integer feedActorId, User actionUser) {
        this.feedActorId = feedActorId;
        this.actionUser = actionUser;
    }

    public abstract PostType getPostType();

    public abstract ActionType getActionType();
}
