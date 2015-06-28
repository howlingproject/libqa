package com.libqa.web.service;

import com.libqa.application.enums.FeedActionTypeEnum;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;

import java.util.Date;

public class FeedActionFactory {
    
    public static FeedAction createLike(Feed feed) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedId(feed.getFeedId());
        feedAction.setCanceled(true);
        feedAction.setFeedActionType(FeedActionTypeEnum.LIKE);
        feedAction.setUserId(feed.getUserId());
        feedAction.setUserNick(feed.getUserNick());
        feedAction.setInsertUserId(feed.getUserId());
        feedAction.setUpdateUserId(feed.getUserId());
        return feedAction;
    }

    public static FeedAction createBy(FeedAction origin) {
        FeedAction target = new FeedAction();
        target.setFeedActionType(origin.getFeedActionType());
        target.setUserId(origin.getUserId());
        target.setUserNick(origin.getUserNick());
        target.setFeedId(origin.getFeedId());
        target.setFeedReplyId(origin.getFeedReplyId());
        target.setCanceled(origin.isCanceled());
        target.setInsertDate(new Date());
        target.setUpdateDate(new Date());
        target.setInsertUserId(origin.getUserId());
        target.setUpdateUserId(origin.getUpdateUserId());
        return target;
    }
}
