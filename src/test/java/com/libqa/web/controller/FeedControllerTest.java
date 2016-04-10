package com.libqa.web.controller;

import com.libqa.application.enums.Role;
import com.libqa.application.util.LoggedUserManager;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.User;
import com.libqa.web.service.feed.FeedFileService;
import com.libqa.web.service.feed.FeedReplyService;
import com.libqa.web.service.feed.FeedThreadService;
import com.libqa.web.view.feed.DisplayFeedActionBuilder;
import com.libqa.web.view.feed.DisplayFeedBuilder;
import com.libqa.web.view.feed.DisplayFeedReplyBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class FeedControllerTest {
    private MockMvc mockMvc;

    @Mock
    private LoggedUserManager loggedUserManager;
    @Mock
    private FeedThreadService feedThreadService;
    @Mock
    private FeedReplyService feedReplyService;
    @Mock
    private FeedFileService feedFileService;
    @Mock
    private DisplayFeedBuilder displayFeedBuilder;
    @Mock
    private DisplayFeedReplyBuilder displayFeedReplyBuilder;
    @Mock
    private DisplayFeedActionBuilder displayFeedActionBuilder;

    @InjectMocks
    private FeedController controller;

    @Before
    public void setup() throws Exception {
        mockMvc = standaloneSetup(controller)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testMain() throws Exception {
        mockMvc.perform(get("/feed"))
                .andExpect(status().isOk())
                .andExpect(view().name("feed/main"));

        verify(displayFeedBuilder).build(anyListOf(FeedThread.class), any(User.class));
    }

    @Test
    public void testRecentlyList() throws Exception {
        mockMvc.perform(get("/feed/recentlyList"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedThreadService).searchRecentlyFeedThreads();
        verify(displayFeedBuilder).build(anyListOf(FeedThread.class), any(User.class));
    }

    @Test
    public void testList() throws Exception {
        final int lastId = 999;
        mockMvc.perform(get("/feed/list?lastId={lastId}", lastId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedThreadService).searchRecentlyFeedThreadsLessThanLastId(lastId);
        verify(displayFeedBuilder).build(anyListOf(FeedThread.class), any(User.class));
    }

    @Test
    public void testView() throws Exception {
        final int feedThreadId = 9999;
        mockMvc.perform(get("/feed/{feedThreadId}", feedThreadId))
                .andExpect(status().isOk())
                .andExpect(view().name("feed/view"));

        verify(feedThreadService).getByFeedThreadId(feedThreadId);
        verify(displayFeedBuilder).build(any(FeedThread.class), any(User.class));
    }

    @Test
    public void testMyList() throws Exception {
        final User user = normalUser();
        final int lastId = 999;

        given(loggedUserManager.getUser()).willReturn(user);

        mockMvc.perform(get("/feed/myList?lastId={lastId}", lastId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedThreadService).searchRecentlyFeedThreadsByUserLessThanLastId(user, lastId);
        verify(displayFeedBuilder).build(anyListOf(FeedThread.class), any(User.class));
    }

    @Test
    public void testSave() throws Exception {
        given(loggedUserManager.getUser()).willReturn(normalUser());

        mockMvc.perform(
                post("/feed/save")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("feedContent", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedThreadService).create(any(FeedThread.class), any(User.class));
    }

    @Test
    public void testSave_withNeedLoginResponse() throws Exception {
        given(loggedUserManager.getUser()).willReturn(guestUser());

        mockMvc.perform(post("/feed/save"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(10)))
                .andExpect(jsonPath("$.comment").value(is("로그인이 필요합니다")));

        verify(feedThreadService, never()).create(any(FeedThread.class), any(User.class));
    }

    @Test
    public void testSave_withFailResponse() throws Exception {
        given(loggedUserManager.getUser()).willReturn(normalUser());
        willThrow(new RuntimeException("unknown Error.")).given(feedThreadService).create(any(FeedThread.class), any(User.class));

        mockMvc.perform(post("/feed/save"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(-1)))
                .andExpect(jsonPath("$.comment").value(is("FAIL")));
    }

    @Test
    public void testModify() throws Exception {
        final User user = mock(User.class);
        final FeedThread feedThreadFixture = feedThreadFixture();

        given(user.isNotMatchUser(feedThreadFixture.getUserId())).willReturn(false);
        given(loggedUserManager.getUser()).willReturn(user);
        given(feedThreadService.getByFeedThreadId(feedThreadFixture.getUserId())).willReturn(feedThreadFixture);

        mockMvc.perform(
                post("/feed/modify")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("feedThreadId", feedThreadFixture.getFeedThreadId().toString())
                        .param("feedContent", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedThreadService).modify(any(FeedThread.class), any(FeedThread.class), any(User.class));
    }

    @Test
    public void testModify_withNotMatchUser() throws Exception {
        final User user = mock(User.class);
        final FeedThread feedThreadFixture = feedThreadFixture();

        given(user.isNotMatchUser(feedThreadFixture.getUserId())).willReturn(true);
        given(loggedUserManager.getUser()).willReturn(user);
        given(feedThreadService.getByFeedThreadId(feedThreadFixture.getUserId())).willReturn(feedThreadFixture);

        mockMvc.perform(
                post("/feed/modify")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("feedThreadId", feedThreadFixture.getFeedThreadId().toString())
                        .param("feedContent", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(11)))
                .andExpect(jsonPath("$.comment").value(is("사용자가 일치하지 않습니다.")));

        verify(feedThreadService, never()).modify(any(FeedThread.class), any(FeedThread.class), any(User.class));
    }

    @Test
    public void testModify_withFailResponse() throws Exception {
        final User user = mock(User.class);
        final FeedThread feedThreadFixture = feedThreadFixture();

        given(user.isNotMatchUser(feedThreadFixture.getUserId())).willReturn(false);
        given(loggedUserManager.getUser()).willReturn(user);
        given(feedThreadService.getByFeedThreadId(feedThreadFixture.getUserId())).willReturn(feedThreadFixture);

        willThrow(new RuntimeException("unknown Error.")).given(feedThreadService).modify(any(FeedThread.class), any(FeedThread.class), any(User.class));

        mockMvc.perform(
                post("/feed/modify")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("feedThreadId", feedThreadFixture.getFeedThreadId().toString())
                        .param("feedContent", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(-1)))
                .andExpect(jsonPath("$.comment").value(is("FAIL")));
    }

    @Test
    public void testDelete() throws Exception {
        final User user = mock(User.class);
        final FeedThread feedThreadFixture = feedThreadFixture();
        final int feedThreadId = 1;

        given(user.isNotMatchUser(feedThreadFixture.getUserId())).willReturn(false);
        given(loggedUserManager.getUser()).willReturn(user);
        given(feedThreadService.getByFeedThreadId(feedThreadFixture.getUserId())).willReturn(feedThreadFixture);

        mockMvc.perform(
                post("/feed/{feedThreadId}/delete", feedThreadId)
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedThreadService).delete(any(FeedThread.class));
    }

    @Test
    public void testLikeFeed() throws Exception {
        final int feedThreadId = 1;
        given(loggedUserManager.getUser()).willReturn(normalUser());

        mockMvc.perform(
                post("/feed/{feedThreadId}/like", feedThreadId)
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedThreadService).like(anyInt(), any(User.class));
        verify(displayFeedActionBuilder).buildLike(any(FeedThread.class), any(User.class));
    }

    @Test
    public void testClaimFeed() throws Exception {
        final int feedThreadId = 1;
        given(loggedUserManager.getUser()).willReturn(normalUser());

        mockMvc.perform(
                post("/feed/{feedThreadId}/claim", feedThreadId)
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedThreadService).claim(anyInt(), any(User.class));
        verify(displayFeedActionBuilder).buildClaim(any(FeedThread.class), any(User.class));
    }

    @Test
    public void testSaveReply() throws Exception {
        given(loggedUserManager.getUser()).willReturn(normalUser());

        mockMvc.perform(
                post("/feed/reply/save")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("feedReplyContent", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedReplyService).create(any(FeedReply.class), any(User.class));
    }

    @Test
    public void testDeleteReply() throws Exception {
        final User user = mock(User.class);
        final FeedReply feedReply = feedReplyFixture();
        final int feedReplyId = 1;

        given(user.isNotMatchUser(feedReply.getUserId())).willReturn(false);
        given(loggedUserManager.getUser()).willReturn(user);
        given(feedReplyService.getByFeedReplyId(feedReply.getUserId())).willReturn(feedReply);

        mockMvc.perform(
                post("/feed/reply/{feedReplyId}/delete", feedReplyId)
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedReplyService).delete(feedReplyId);
    }

    @Test
    public void testLikeReply() throws Exception {
        final int feedReplyId = 1;
        given(loggedUserManager.getUser()).willReturn(normalUser());

        mockMvc.perform(
                post("/feed/reply/{feedReplyId}/like", feedReplyId)
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedReplyService).like(anyInt(), any(User.class));
        verify(displayFeedActionBuilder).buildLike(any(FeedReply.class), any(User.class));
    }

    @Test
    public void testClaimReply() throws Exception {
        final int feedReplyId = 1;
        given(loggedUserManager.getUser()).willReturn(normalUser());

        mockMvc.perform(
                post("/feed/reply/{feedReplyId}/claim", feedReplyId)
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedReplyService).claim(anyInt(), any(User.class));
        verify(displayFeedActionBuilder).buildClaim(any(FeedReply.class), any(User.class));
    }

    @Test
    public void testDeleteFile() throws Exception {
        final int feedFileId = 1;
        given(loggedUserManager.getUser()).willReturn(normalUser());
        given(feedFileService.getByFeedFileId(feedFileId)).willReturn(new FeedFile());

        mockMvc.perform(
                post("/feed/file/{feedFileId}/delete", feedFileId)
                        .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resultCode").value(is(1)))
                .andExpect(jsonPath("$.comment").value(is("SUCCESS")));

        verify(feedFileService).delete(any(FeedFile.class));
    }

    private User guestUser() {
        return User.createGuest();
    }

    private User normalUser() {
        User user = new User();
        user.setRole(Role.USER);
        user.setUserNick("nickname");
        return user;
    }

    private FeedThread feedThreadFixture() {
        final FeedThread feedThread = new FeedThread();
        feedThread.setFeedThreadId(1);
        feedThread.setFeedContent("test");
        feedThread.setUserId(1);
        return feedThread;
    }

    private FeedReply feedReplyFixture() {
        FeedReply feedReply = new FeedReply();
        feedReply.setFeedReplyId(1);
        feedReply.setFeedReplyContent("replyContent");
        feedReply.setUserId(1);
        return feedReply;
    }
}
