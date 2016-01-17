package com.libqa.web.repository;

import com.libqa.testsupport.LibqaRepositoryTest;
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
    public void countByFeedId() {
        Integer count = repository.countByFeedId(-1);
        assertThat(count).isZero();
    }

}
