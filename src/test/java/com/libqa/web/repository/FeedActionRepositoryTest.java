package com.libqa.web.repository;

import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.FeedAction;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FeedActionRepositoryTest extends LibqaRepositoryTest {

    @Autowired
    FeedActionRepository repository;

    @Test
    public void findByFeedIdAndUserId() {
        final Integer feedId = -1;
        final Integer userId = 1234;
        List<FeedAction> actual = repository.findByFeedIdAndUserId(feedId, userId);
        assertThat(actual.size(), is(0));
    }

    @Test
    public void findByFeedReplyIdAndUserId() {
        final Integer feedReplyId = -1;
        final Integer userId = 1234;

        List<FeedAction> actual = repository.findByFeedReplyIdAndUserId(feedReplyId, userId);
        assertThat(actual.size(), is(0));
    }
}
