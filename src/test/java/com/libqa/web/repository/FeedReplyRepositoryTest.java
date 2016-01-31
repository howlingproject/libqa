package com.libqa.web.repository;

import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class FeedReplyRepositoryTest extends LibqaRepositoryTest<FeedReplyRepository> {

    @Test
    public void findOne() {
        FeedReply actual = repository.findOne(-1);
        assertThat(actual).isNull();
    }

    @Test
    public void countByFeedAndIsDeletedFalse() {
        Feed feed = new Feed();
        feed.setFeedId(10000);
        Integer count = repository.countByFeedAndIsDeletedFalse(feed);
        assertThat(count).isZero();
    }

}
