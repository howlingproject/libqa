package com.libqa.web.repository;

import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.FeedAction;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FeedActionRepositoryTest extends LibqaRepositoryTest<FeedActionRepository> {

    @Test
    public void findByFeedIdAndUserId() {
        final Integer feedId = -1;
        final Integer userId = 1234;
        List<FeedAction> actual = repository.findByFeedActorIdAndUserId(feedId, userId);
        assertThat(actual.size(), is(0));
    }

    @Test
    public void findByFeedReplyIdAndUserId() {
        final Integer feedReplyId = -1;
        final Integer userId = 1234;

        List<FeedAction> actual = repository.findByFeedActorIdAndUserId(feedReplyId, userId);
        assertThat(actual.size(), is(0));
    }
}
