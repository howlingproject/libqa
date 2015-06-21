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

    public boolean isLikable(FeedLikeUser feedLikeUser) {
        return feedLikeUser == null ||  feedLikeUser.isCanceled();
    }

    public FeedLikeUser getRecentlyFeedLikeUserBy(Feed feed) {
        List<FeedLikeUser> feedLikeUsers = repository.findByFeedAndUserIdAndFeedLikeType(feed, feed.getUserId(), FEED);
        return Iterables.getLast(feedLikeUsers, FeedLikeUser.createNew(feed, FEED));
    }

    public void like(FeedLikeUser feedLikeUser) {
        save(feedLikeUser, false);
    }

    public void disLike(FeedLikeUser feedLikeUser) {
        save(feedLikeUser, true);
    }

    private void save(FeedLikeUser feedLikeUser, boolean isCanceled) {
        feedLikeUser.setCanceled(isCanceled);
        feedLikeUser.setInsertDate(new Date());
        feedLikeUser.setUpdateDate(new Date());
        repository.save(feedLikeUser);
    }
}
