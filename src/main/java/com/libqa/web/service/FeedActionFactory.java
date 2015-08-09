package com.libqa.web.service;

import com.libqa.application.enums.FeedActionTypeEnum;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;

public class FeedActionFactory {

    public static FeedAction createLike(Feed feed) {
        FeedAction feedAction = createFeedAction(feed);
        feedAction.setFeedActionType(FeedActionTypeEnum.LIKE);
        return feedAction;
    }

    public static FeedAction createClaim(Feed feed) {
        FeedAction feedAction = createFeedAction(feed);
        feedAction.setFeedActionType(FeedActionTypeEnum.CLAIM);
        return feedAction;
    }

    public static FeedAction createLike(FeedReply feedReply) {
        FeedAction feedAction = createFeedAction(feedReply);
        feedAction.setFeedActionType(FeedActionTypeEnum.LIKE);
        return feedAction;
    }

    public static FeedAction createClaim(FeedReply feedReply) {
        FeedAction feedAction = createFeedAction(feedReply);
        feedAction.setFeedActionType(FeedActionTypeEnum.CLAIM);
        return feedAction;
    }

    private static FeedAction createFeedAction(Feed feed) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedId(feed.getFeedId());
        feedAction.setUserId(feed.getUserId());
        feedAction.setUserNick(feed.getUserNick());
        feedAction.setInsertUserId(feed.getUserId());
        feedAction.setUpdateUserId(feed.getUserId());
        feedAction.setCanceled(false);
        return feedAction;
    }

    private static FeedAction createFeedAction(FeedReply feedReply) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedId(feedReply.getFeed().getFeedId());
        feedAction.setFeedReplyId(feedReply.getFeedReplyId());
        feedAction.setUserId(feedReply.getUserId());
        feedAction.setUserNick(feedReply.getUserNick());
        feedAction.setInsertUserId(feedReply.getUserId());
        feedAction.setUpdateUserId(feedReply.getUserId());
        feedAction.setCanceled(false);
        return feedAction;
    }
}
