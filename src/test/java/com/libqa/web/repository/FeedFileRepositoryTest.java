package com.libqa.web.repository;

import com.libqa.testsupport.LibqaRepositoryTest;
import org.junit.Test;

public class FeedFileRepositoryTest extends LibqaRepositoryTest<FeedFileRepository> {

    @Test
    public void findByFeedFeedThreadId() {
        final int feedThreadId = 10000;
        repository.findByFeedThreadFeedThreadId(feedThreadId);
    }
}