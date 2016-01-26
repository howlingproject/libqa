package com.libqa.web.view.feed;

import com.google.common.collect.Lists;
import com.libqa.web.domain.Feed;
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
     * feed 목록을 display 용으로 build 한다.
     *
     * @param feeds  list of feed
     * @param viewer viewer
     * @return List&lt;DisplayFeed&gt;
     */
    public List<DisplayFeed> build(List<Feed> feeds, User viewer) {
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        displayFeeds.addAll(feeds.stream()
                .map(feed -> build(feed, viewer))
                .collect(Collectors.toList()));
        return displayFeeds;
    }

    /**
     * feed를 display 용으로 build 한다.
     *
     * @param feed   feed
     * @param viewer viewer
     * @return DisplayFeed
     */
    public DisplayFeed build(Feed feed, User viewer) {
        if (feed == null || feed.isDeleted()) {
            return DisplayFeed.empty();
        }

        User writer = userService.findByUserId(feed.getUserId());
        DisplayFeedAction likedFeedAction = displayFeedActionBuilder.buildLikeBy(feed, viewer);
        DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.buildClaimBy(feed, viewer);

        final boolean isWriter = writer.isMatchUser(viewer.getUserId());

        DisplayFeed displayFeed = new DisplayFeed(feed, writer, isWriter);
        displayFeed.setLikeFeedAction(likedFeedAction);
        displayFeed.setClaimFeedAction(claimedFeedAction);
        displayFeed.setReplies(displayFeedReplyBuilder.build(feed.getFeedReplies(), viewer));
        return displayFeed;
    }
}
