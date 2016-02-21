package com.libqa.web.service.index;

import com.google.common.collect.Lists;
import com.libqa.web.domain.*;
import com.libqa.web.service.common.KeywordService;
import com.libqa.web.service.feed.FeedReplyService;
import com.libqa.web.service.feed.FeedThreadService;
import com.libqa.web.service.qa.QaReplyService;
import com.libqa.web.service.qa.QaService;
import com.libqa.web.service.space.SpaceService;
import com.libqa.web.service.user.UserService;
import com.libqa.web.service.wiki.WikiService;
import com.libqa.web.view.index.DisplayIndex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static com.libqa.web.service.index.IndexCrawler.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class IndexCrawlerTest {
    private static final Integer ANY_USER_ID = 0;

    @Mock
    private UserService userService;
    @Mock
    private QaService qaService;
    @Mock
    private QaReplyService qaReplyService;
    @Mock
    private KeywordService keywordService;
    @Mock
    private SpaceService spaceService;
    @Mock
    private WikiService wikiService;
    @Mock
    private FeedThreadService feedThreadService;
    @Mock
    private FeedReplyService feedReplyService;

    @InjectMocks
    private IndexCrawler sut;

    @Test
    public void crawl() {
        final List<Wiki> expectedNoticeWikies = noticeForWikies();
        final List<QaContent> expectedQaContents = Lists.newArrayList(qaContentFixture(), qaContentFixture());
        final List<Space> expectedSpaces = Lists.newArrayList(mock(Space.class), mock(Space.class));
        final List<Wiki> expectedWikies = Lists.newArrayList(mock(Wiki.class));
        final List<FeedThread> expectedFeedThreads = Lists.newArrayList(feedThreadFixture());

        given(wikiService.findAllLatestWikiBySpaceId(anyInt(), anyInt())).willReturn(expectedNoticeWikies);
        given(userService.findByUserId(ANY_USER_ID)).willReturn(mock(User.class));
        given(qaService.searchRecentlyQaContentsByPageSize(INDEX_QA_SIZE)).willReturn(expectedQaContents);
        given(spaceService.findAllByCondition(false, 0, INDEX_SPACE_SIZE)).willReturn(expectedSpaces);
        given(wikiService.searchRecentlyWikiesByPageSize(INDEX_WIKI_SIZE)).willReturn(expectedWikies);
        given(feedThreadService.searchRecentlyFeedThreadsByPageSize(INDEX_FEED_SIZE)).willReturn(expectedFeedThreads);

        DisplayIndex actual = sut.crawl();

        assertThat(actual.getNotice().getTitle()).isEqualTo("wiki1");
        assertThat(actual.getQaContents().size()).isEqualTo(2);
        assertThat(actual.getSpaces().size()).isEqualTo(2);
        assertThat(actual.getWikies().size()).isEqualTo(1);
        assertThat(actual.getFeeds().size()).isEqualTo(1);
    }

    @Test
    public void crawlWithEmptyList() {
        DisplayIndex actual = sut.crawl();

        assertThat(actual.getNotice().isEmpty()).isTrue();
        assertThat(actual.getQaContents().size()).isZero();
        assertThat(actual.getSpaces().size()).isZero();
        assertThat(actual.getWikies().size()).isZero();
        assertThat(actual.getFeeds().size()).isZero();
    }

    private QaContent qaContentFixture() {
        QaContent qaContent = new QaContent();
        qaContent.setUserId(ANY_USER_ID);
        qaContent.setInsertDate(new Date());
        return qaContent;
    }

    private FeedThread feedThreadFixture() {
        FeedThread feedThread = new FeedThread();
        feedThread.setUserId(ANY_USER_ID);
        feedThread.setInsertDate(new Date());
        return feedThread;
    }

    private List<Wiki> noticeForWikies() {
        Wiki wiki1 = new Wiki();
        wiki1.setWikiId(1);
        wiki1.setTitle("wiki1");
        wiki1.setInsertDate(new Date());

        return Lists.newArrayList(wiki1);
    }
}