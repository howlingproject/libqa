package com.libqa.web.repository;

import com.libaqa.testsupport.LibQaIntegrationTest;
import com.libqa.web.domain.Feed;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertNull;

@Slf4j
public class FeedRepositoryIntegrationTest extends LibQaIntegrationTest {
    @Autowired
    private FeedRepository feedRepository;
    
    @Test
    public void testFindOne() {
        Feed actual = feedRepository.findOne(-1L);
        assertNull(actual);
    }

}
