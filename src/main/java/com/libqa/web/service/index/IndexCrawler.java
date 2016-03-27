package com.libqa.web.service.index;

import com.google.common.collect.Lists;
import com.libqa.config.CacheConfiguration;
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

import static com.google.common.base.MoreObjects.firstNonNull;

@Service
public class IndexCrawler {
    private static final Integer ZERO = 0;
    static final Integer INDEX_NOTICE_SIZE = 1;
    static final Integer INDEX_QA_SIZE = 3;
    static final Integer INDEX_SPACE_SIZE = 3;
    static final Integer INDEX_WIKI_SIZE = 3;
    static final Integer INDEX_FEED_SIZE = 10;

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
     * 인덱스에 노출할 정보를 crawl 하여 {@link DisplayIndex}에 담아 반환한다.
     *
     * @return DisplayIndex
     */
    @Cacheable(CacheConfiguration.CACHE_DISPLAY_INDEX)
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
        List<Wiki> wikies = wikiService.findAllLatestWikiBySpaceId(INDEX_NOTICE_SIZE, spaceIdForNotice);
        if (wikies.isEmpty()) {
            return IndexNotice.empty();
        }

        Wiki wiki = wikies.stream().findFirst().get();
        String viewUrl = String.format("/wiki/%s", wiki.getWikiId());
        String moreUrl = String.format("/space/%s", spaceIdForNotice);

        IndexNotice indexNotice = IndexNotice.of();
        indexNotice.setViewUrl(viewUrl);
        indexNotice.setMoreUrl(moreUrl);
        indexNotice.setTitle(wiki.getTitle());
        indexNotice.setContents(wiki.getContents());
        indexNotice.setInsertDate(DisplayDate.parse(wiki.getInsertDate()));
        indexNotice.setCountOfReply(firstNonNull(wiki.getReplyCount(), ZERO));
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
            qaContent.setCountOfReply(firstNonNull(qaReplyService.countByQaContent(each), ZERO));
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
        List<FeedThread> feedThreads = feedThreadService.searchRecentlyFeedThreadsByPageSize(INDEX_FEED_SIZE);
        for (FeedThread each : feedThreads) {
            User writer = getWriterByUserId(each.getUserId());

            IndexFeed indexFeed = IndexFeed.of();
            indexFeed.setFeedThreadId(each.getFeedThreadId());
            indexFeed.setFeedContent(each.getFeedContent());
            indexFeed.setCountOfReply(firstNonNull(each.getReplyCount(), ZERO));
            indexFeed.setUserNick(writer.getUserNick());
            indexFeed.setUserImage(writer.getUserImage());
            result.add(indexFeed);
        }
        return result;
    }

    private User getWriterByUserId(Integer userId) {
        return userService.findByUserId(userId);
    }
}
