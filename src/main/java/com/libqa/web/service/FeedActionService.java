package com.libqa.web.service;

import com.google.common.collect.Iterables;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.repository.FeedActionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.libqa.application.enums.FeedActionTypeEnum.LIKE;

@Slf4j
@Service
public class FeedActionService {
    @Autowired
    private FeedActionRepository repository;

    public boolean isLikable(FeedAction feedAction) {
        return feedAction == null ||  feedAction.isCanceled();
    }

    public FeedAction getRecentlyFeedLikeUserBy(Feed feed) {
        List<FeedAction> feedActions = repository.findByFeedIdAndUserIdAndFeedActionType(feed.getFeedId(), feed.getUserId(), LIKE);
        return Iterables.getLast(feedActions, FeedActionFactory.createLike(feed));
    }

    public void like(FeedAction feedAction) {
        save(feedAction, false);
    }

    public void disLike(FeedAction feedAction) {
        save(feedAction, true);
    }

    private void save(FeedAction feedAction, boolean isCanceled) {
        FeedAction newFeedAction = FeedActionFactory.createBy(feedAction);
        newFeedAction.setCanceled(isCanceled);
        repository.save(newFeedAction);
    }
}
