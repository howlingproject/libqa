package com.libqa.web.repository;

import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.FeedReply;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class FeedReplyRepositoryTest extends LibqaRepositoryTest<FeedReplyRepository> {
    @Test
    public void findOne() {
        FeedReply actual = repository.findOne(-1);
        assertNull(actual);
    }
}
