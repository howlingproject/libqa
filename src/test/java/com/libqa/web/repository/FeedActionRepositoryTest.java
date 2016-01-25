package com.libqa.web.repository;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.FeedAction;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class FeedActionRepositoryTest extends LibqaRepositoryTest<FeedActionRepository> {

    @Test
    public void findByFeedActorIdAndUserIdAndIsCanceledFalse() {
        final Integer feedId = -1;
        final Integer userId = 1234;
        List<FeedAction> actual = repository.findByFeedActorIdAndUserIdAndIsCanceledFalse(feedId, userId);
        assertThat(actual.size()).isZero();
    }

    @Test
    public void countByFeedActorIdAndFeedThreadTypeAndFeedActionTypeAndIsCanceledFalse() {
        final Integer feedReplyId = -1;
        final FeedThreadType feedThreadType = FeedThreadType.FEED_REPLY;
        final FeedActionType feedActionType = FeedActionType.LIKE;

        int count = repository.countByFeedActorIdAndFeedThreadTypeAndFeedActionTypeAndIsCanceledFalse(
                feedReplyId, feedThreadType, feedActionType);
        assertThat(count).isZero();
    }
}
