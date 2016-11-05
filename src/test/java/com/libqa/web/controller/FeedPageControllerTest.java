package com.libqa.web.controller;

import com.libqa.application.util.LoggedUserManager;
import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.User;
import com.libqa.web.service.feed.FeedThreadService;
import com.libqa.web.view.feed.DisplayFeedBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class FeedPageControllerTest {
    private MockMvc mockMvc;

    @Mock
    private LoggedUserManager loggedUserManager;
    @Mock
    private DisplayFeedBuilder displayFeedBuilder;
    @Mock
    private FeedThreadService feedThreadService;

    @InjectMocks
    private FeedPageController controller;


    @Before
    public void setup() throws Exception {
        mockMvc = standaloneSetup(controller)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void mainPage() throws Exception {
        mockMvc.perform(get("/feed"))
                .andExpect(status().isOk())
                .andExpect(view().name("feed/main"));

        verify(displayFeedBuilder).build(anyListOf(FeedThread.class), any(User.class));
    }


    @Test
    public void viewPage() throws Exception {
        final int feedThreadId = 9999;
        mockMvc.perform(get("/feed/{feedThreadId}", feedThreadId))
                .andExpect(status().isOk())
                .andExpect(view().name("feed/view"));

        verify(feedThreadService).getByFeedThreadId(feedThreadId);
        verify(displayFeedBuilder).build(any(FeedThread.class), any(User.class));
    }

}