package com.libqa.web.service.index;

import com.google.common.collect.Lists;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.User;
import com.libqa.web.service.feed.FeedReplyService;
import com.libqa.web.service.feed.FeedService;
import com.libqa.web.service.user.UserService;
import com.libqa.web.view.index.DisplayIndex;
import com.libqa.web.view.index.IndexFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexCrawler {
    private static final Integer INDEX_FEED_SIZE = 8;

    @Autowired
    private UserService userService;
    @Autowired
    private FeedService feedService;
    @Autowired
    private FeedReplyService feedReplyService;

    /**
     * index에 노출할 정보를 crawling하여 반환한다.
     *
     * @return DisplayIndex
     */
    public DisplayIndex crawl() {
        DisplayIndex displayIndex = new DisplayIndex();
        displayIndex.setFeeds(buildFeeds());
        return displayIndex;
    }

    private List<IndexFeed> buildFeeds() {
        List<IndexFeed> result = Lists.newArrayList();
        List<Feed> feeds = feedService.searchByPageSize(INDEX_FEED_SIZE);
        for (Feed each : feeds) {
            User writer = getWriterByUserId(each.getUserId());

            IndexFeed indexFeed = IndexFeed.of();
            indexFeed.setContent(each.getFeedContent());
            indexFeed.setUserImage(writer.getUserImage());
            indexFeed.setCountOfReply(feedReplyService.countByFeed(each));

            result.add(indexFeed);
        }
        return result;
    }

    private User getWriterByUserId(Integer userId) {
        return userService.findByUserId(userId);
    }
}
