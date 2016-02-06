package com.libqa.web.view.feed;

import com.google.common.collect.Lists;
import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.User;
import com.libqa.web.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisplayFeedBuilder {
    @Autowired
    private UserService userService;
    @Autowired
    private DisplayFeedReplyBuilder displayFeedReplyBuilder;
    @Autowired
    private DisplayFeedActionBuilder displayFeedActionBuilder;

    /**
     * feedThread 목록을 display 용으로 build 한다.
     *
     * @param feedThreads list of feedThread
     * @param viewer      viewer
     * @return List&lt;DisplayFeed&gt;
     */
    public List<DisplayFeed> build(List<FeedThread> feedThreads, User viewer) {
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        displayFeeds.addAll(
                feedThreads.stream()
                        .map(feed -> build(feed, viewer))
                        .collect(Collectors.toList()));
        return displayFeeds;
    }

    /**
     * feed를 display 용으로 build 한다.
     *
     * @param feedThread feedThread
     * @param viewer     viewer
     * @return DisplayFeed
     */
    public DisplayFeed build(FeedThread feedThread, User viewer) {
        if (feedThread == null || feedThread.isDeleted()) {
            return DisplayFeed.empty();
        }

        User writer = userService.findByUserId(feedThread.getUserId());
        DisplayFeedAction likedFeedAction = displayFeedActionBuilder.buildLike(feedThread, viewer);
        DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.buildClaim(feedThread, viewer);

        final boolean isWriter = writer.isMatchUser(viewer.getUserId());

        DisplayFeed displayFeed = new DisplayFeed(feedThread, writer, isWriter);
        displayFeed.setLikeFeedAction(likedFeedAction);
        displayFeed.setClaimFeedAction(claimedFeedAction);
        displayFeed.setReplies(displayFeedReplyBuilder.build(feedThread, viewer));
        return displayFeed;
    }
}
