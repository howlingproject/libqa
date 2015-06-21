package com.libqa.web.service;

import com.google.common.collect.Iterables;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedLikeUser;
import com.libqa.web.repository.FeedLikeUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.libqa.application.enums.FeedLikeTypeEnum.FEED;

@Slf4j
@Service
public class FeedLikeUserService {
    @Autowired
    private FeedLikeUserRepository repository;

    public void likeOrUnlike(Feed feed) {
        List<FeedLikeUser> feedLikeUsers = repository.findByFeedAndUserIdAndFeedLikeType(feed, feed.getUserId(), FEED);
        FeedLikeUser latestFeedLikeUser = getLatestFeedLikeUser(feedLikeUsers);
        if (isLikable(latestFeedLikeUser)) {
            like(feed);
        } else {
            disLike(feed);
        }
    }

    private boolean isLikable(FeedLikeUser latestFeedLikeUser) {
        return latestFeedLikeUser == null || latestFeedLikeUser.isCanceled();
    }

    private FeedLikeUser getLatestFeedLikeUser(List<FeedLikeUser> feedLikeUsers) {
//        return Iterables.getLast(feedLikeUsers, null);
        return Iterables.getLast(feedLikeUsers);
    }

    private void like(Feed feed) {
        saveByFeed(feed, false);
    }

    private void disLike(Feed feed) {
        saveByFeed(feed, true);
    }

    private void saveByFeed(Feed feed, boolean isCanceled) {
        FeedLikeUser feedLikeUser = new FeedLikeUser();
        feedLikeUser.setFeedLikeType(FEED);
        feedLikeUser.setReplyId(-1l);
        feedLikeUser.setCanceled(isCanceled);
        feedLikeUser.setInsertDate(new Date());
        feedLikeUser.setUpdateDate(new Date());
        feedLikeUser.setUserId(feed.getUserId());
        feedLikeUser.setUserNick(feed.getUserNick());
        feedLikeUser.setInsertUserId(feed.getUserId());
        feedLikeUser.setUpdateUserId(feed.getUserId());
        feedLikeUser.setFeed(feed);
        repository.save(feedLikeUser);
    }
}
