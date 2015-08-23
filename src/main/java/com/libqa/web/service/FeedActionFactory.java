package com.libqa.web.service;

import com.libqa.application.enums.FeedActionTypeEnum;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;

import java.util.Date;

public class FeedActionFactory {

    public static FeedAction createLike(Feed feed, User user) {
        FeedAction feedAction = createFeedAction(feed, user);
        feedAction.setFeedActionType(FeedActionTypeEnum.LIKE);
        return feedAction;
    }

    public static FeedAction createClaim(Feed feed, User user) {
        FeedAction feedAction = createFeedAction(feed, user);
        feedAction.setFeedActionType(FeedActionTypeEnum.CLAIM);
        return feedAction;
    }

    public static FeedAction createLike(FeedReply feedReply, User user) {
        FeedAction feedAction = createFeedAction(feedReply, user);
        feedAction.setFeedActionType(FeedActionTypeEnum.LIKE);
        return feedAction;
    }

    public static FeedAction createClaim(FeedReply feedReply, User user) {
        FeedAction feedAction = createFeedAction(feedReply, user);
        feedAction.setFeedActionType(FeedActionTypeEnum.CLAIM);
        return feedAction;
    }

    private static FeedAction createFeedAction(Feed feed, User user) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedId(feed.getFeedId());
        feedAction.setUserId(user.getUserId());
        feedAction.setUserNick(user.getUserNick());
        feedAction.setInsertUserId(user.getUserId());
        feedAction.setUpdateUserId(user.getUserId());
        feedAction.setCanceled(false);
        feedAction.setInsertDate(new Date());
        feedAction.setUpdateDate(new Date());
        return feedAction;
    }

    private static FeedAction createFeedAction(FeedReply feedReply, User user) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedId(feedReply.getFeed().getFeedId());
        feedAction.setFeedReplyId(feedReply.getFeedReplyId());
        feedAction.setUserId(user.getUserId());
        feedAction.setUserNick(user.getUserNick());
        feedAction.setInsertUserId(user.getUserId());
        feedAction.setUpdateUserId(user.getUserId());
        feedAction.setCanceled(false);
        feedAction.setInsertDate(new Date());
        feedAction.setUpdateDate(new Date());
        return feedAction;
    }
}
