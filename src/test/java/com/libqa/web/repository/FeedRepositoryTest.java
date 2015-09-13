package com.libqa.web.repository;

import com.libaqa.testsupport.LibQaIntegrationTest;
import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.Feed;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
public class FeedRepositoryTest extends LibQaIntegrationTest {
    @Autowired
    private FeedRepository repository;
    
    @Test
    public void testFindOne() {
        Feed actual = repository.findOne(-1);
        assertNull(actual);
    }

    @Test
    public void searchWithPageRequest() {
        int lastFeedId = 0;
        PageRequest pageRequest = PageUtil.sortPageable(new Sort(DESC, "feedId"));
        List<Feed> feeds = repository.findByFeedIdLessThanAndIsDeletedFalse(lastFeedId, pageRequest);
        System.out.println(feeds);
    }
}
