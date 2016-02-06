package com.libqa.web.service.index;

import com.google.common.collect.Lists;
import com.libqa.config.CacheConfig;
import com.libqa.web.domain.*;
import com.libqa.web.service.common.KeywordService;
import com.libqa.web.service.feed.FeedThreadService;
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

import static java.util.Comparator.comparing;

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
    private FeedThreadService feedThreadService;

    /**
     * 인덱스에 노출할 정보를 crawling하여 {@link DisplayIndex}에 담아 반환한다.
     *
     * @return DisplayIndex
     */
    @Cacheable(CacheConfig.CACHE_DISPLAY_INDEX)
    public DisplayIndex crawl() {
        DisplayIndex displayIndex = DisplayIndex.of();
        displayIndex.setNotice(buildNotice());
        displayIndex.setQaContents(buildQaContents());
        displayIndex.setSpaces(buildSpaces());
        displayIndex.setWikies(buildWikies());
        displayIndex.setFeeds(buildFeeds());
        return displayIndex;
    }

    private IndexNotice buildNotice() {
        final int spaceIdForNotice = 1;
        List<Wiki> wikies = wikiService.findBySpaceId(spaceIdForNotice);
        if (wikies.isEmpty()) {
            return IndexNotice.empty();
        }

        Wiki wiki = wikies.stream()
                .sorted(comparing(Wiki::getWikiId))
                .findFirst().get();
        String noticeUrl = String.format("/wiki/%s", wiki.getWikiId());

        IndexNotice indexNotice = IndexNotice.of();
        indexNotice.setUrl(noticeUrl);
        indexNotice.setTitle(wiki.getTitle());
        indexNotice.setContents(wiki.getContents());
        indexNotice.setInsertDate(DisplayDate.parse(wiki.getInsertDate()));
        indexNotice.setCountOfReply(0); // TODO wiki의 replyCount 로 대체
        return indexNotice;
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
        List<Wiki> wikies = wikiService.searchRecentlyWikiesByPageSize(INDEX_WIKI_SIZE);
        for (Wiki each : wikies) {
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
        List<FeedThread> feedThreads = feedThreadService.searchRecentlyFeedsByPageSize(INDEX_FEED_SIZE);
        for (FeedThread each : feedThreads) {
            User writer = getWriterByUserId(each.getUserId());

            IndexFeed indexFeed = IndexFeed.of();
            indexFeed.setFeedThreadId(each.getFeedThreadId());
            indexFeed.setFeedContent(each.getFeedContent());
            indexFeed.setCountOfReply(each.getReplyCount());
            indexFeed.setUserImage(writer.getUserImage());
            result.add(indexFeed);
        }
        return result;
    }

    private User getWriterByUserId(Integer userId) {
        return userService.findByUserId(userId);
    }
}
