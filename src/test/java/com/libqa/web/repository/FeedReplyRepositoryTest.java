package com.libqa.web.repository;

import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.FeedThread;
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
        FeedThread feedThread = new FeedThread();
        feedThread.setFeedThreadId(10000);
        Integer count = repository.countByFeedThreadAndIsDeletedFalse(feedThread);
        assertThat(count).isZero();
    }

}
