package com.libqa.web.service.index;

import com.google.common.collect.Lists;
import com.libqa.config.CacheConfig;
import com.libqa.web.domain.*;
import com.libqa.web.service.common.KeywordService;
import com.libqa.web.service.feed.FeedReplyService;
import com.libqa.web.service.feed.FeedService;
import com.libqa.web.service.qa.QaReplyService;
import com.libqa.web.service.qa.QaService;
import com.libqa.web.service.space.SpaceService;
import com.libqa.web.service.user.UserService;
import com.libqa.web.service.wiki.WikiService;
import com.libqa.web.view.feed.DisplayDate;
import com.libqa.web.view.index.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexCrawler {
    static final Integer INDEX_QA_SIZE = 3;
    static final Integer INDEX_SPACE_SIZE = 3;
    static final Integer INDEX_WIKI_SIZE = 3;
    static final Integer INDEX_FEED_SIZE = 9;

    @Autowired
    private UserService userService;
    @Autowired
    private QaService qaService;
    @Autowired
    private QaReplyService qaReplyService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private WikiService wikiService;
    @Autowired
    private FeedService feedService;
    @Autowired
    private FeedReplyService feedReplyService;

    /**
     * 인덱스에 노출할 정보를 crawling하여 {@link DisplayIndex}에 담아 반환한다.
     *
     * @return DisplayIndex
     */
    @Cacheable(CacheConfig.CACHE_DISPLAY_INDEX)
    public DisplayIndex crawl() {
        DisplayIndex displayIndex = DisplayIndex.of();
        displayIndex.setQaContents(buildQaContents());
        displayIndex.setSpaces(buildSpaces());
        displayIndex.setWikies(buildWikies());
        displayIndex.setFeeds(buildFeeds());
        return displayIndex;
    }

    private List<IndexQaContent> buildQaContents() {
        List<IndexQaContent> result = Lists.newArrayList();
        List<QaContent> qaContents = qaService.searchRecentlyQaContentsByPageSize(INDEX_QA_SIZE);
        for (QaContent each : qaContents) {
            User writer = getWriterByUserId(each.getUserId());
            List<Keyword> keywords = keywordService.findByQaId(each.getQaId());

            IndexQaContent qaContent = IndexQaContent.of();
            qaContent.setQaId(each.getQaId());
            qaContent.setTitle(each.getTitle());
            qaContent.setContents(each.getContents());
            qaContent.setUserNick(each.getUserNick());
            qaContent.setUserImage(writer.getUserImage());
            qaContent.setKeywords(buildIndexKeywords(keywords));
            qaContent.setInsertDate(DisplayDate.parse(each.getInsertDate()));
            qaContent.setCountOfReply(qaReplyService.countByQaContent(each));
            result.add(qaContent);
        }
        return result;
    }

    private List<IndexKeyword> buildIndexKeywords(List<Keyword> keywords) {
        List<IndexKeyword> result = Lists.newArrayList();
        for (Keyword each : keywords) {
            IndexKeyword indexKeyword = IndexKeyword.of();
            indexKeyword.setName(each.getKeywordName());
            result.add(indexKeyword);
        }
        return result;
    }

    private List<IndexSpace> buildSpaces() {
        List<IndexSpace> result = Lists.newArrayList();

        final boolean isDeleted = false;
        final int startIdx = 0;
        List<Space> spaces = spaceService.findAllByCondition(isDeleted, startIdx, INDEX_SPACE_SIZE);
        for (Space each : spaces) {
            IndexSpace space = IndexSpace.of();
            space.setSpaceId(each.getSpaceId());
            space.setTitle(each.getTitle());
            space.setDescription(each.getDescription());
            result.add(space);
        }
        return result;
    }

    private List<IndexWiki> buildWikies() {
        List<IndexWiki> result = Lists.newArrayList();
        List<Wiki> wikes = wikiService.searchRecentlyWikiesByPageSize(INDEX_WIKI_SIZE);
        for (Wiki each : wikes) {
            IndexWiki indexWiki = IndexWiki.of();
            indexWiki.setWikiId(each.getWikiId());
            indexWiki.setTitle(each.getTitle());
            indexWiki.setDescription(each.getContents());
            result.add(indexWiki);
        }
        return result;
    }

    private List<IndexFeed> buildFeeds() {
        List<IndexFeed> result = Lists.newArrayList();
        List<Feed> feeds = feedService.searchRecentlyFeedsByPageSize(INDEX_FEED_SIZE);
        for (Feed each : feeds) {
            User writer = getWriterByUserId(each.getUserId());

            IndexFeed indexFeed = IndexFeed.of();
            indexFeed.setFeedId(each.getFeedId());
            indexFeed.setFeedContent(each.getFeedContent());
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
