package com.libqa.web.repository;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.PostType;
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
    public void countByFeedActorIdAndPostTypeAndActionTypeAndIsCanceledFalse() {
        final Integer feedReplyId = -1;
        final PostType postType = PostType.REPLY;
        final ActionType actionType = ActionType.LIKE;

        int count = repository.countByFeedActorIdAndPostTypeAndActionTypeAndIsCanceledFalse(
                feedReplyId, postType, actionType);

        assertThat(count).isZero();
    }
}
