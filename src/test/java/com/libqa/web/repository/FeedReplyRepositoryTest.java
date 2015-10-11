package com.libqa.web.repository;

import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.FeedReply;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;

public class FeedReplyRepositoryTest extends LibqaRepositoryTest {
    @Autowired
    FeedReplyRepository repository;

    @Test
    public void findOne() {
        FeedReply actual = repository.findOne(-1);
        assertNull(actual);
    }
}
