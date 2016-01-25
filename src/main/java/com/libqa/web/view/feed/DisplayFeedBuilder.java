package com.libqa.web.view.feed;

import com.google.common.collect.Lists;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisplayFeedBuilder {
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private UserService userService;
    @Autowired
    private DisplayFeedReplyBuilder displayFeedReplyBuilder;
    @Autowired
    private DisplayFeedActionBuilder displayFeedActionBuilder;

    /**
     * feed 목록을 display 용으로 build 한다.
     *
     * @param feeds list of feed
     * @return List&lt;DisplayFeed&gt;
     */
    public List<DisplayFeed> build(List<Feed> feeds) {
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        displayFeeds.addAll(feeds.stream()
                .map(feed -> build(feed, feed.getFeedReplies()))
                .collect(Collectors.toList()));
        return displayFeeds;
    }

    /**
     * feed를 display 용으로 build 한다.
     *
     * @param feed feed
     * @return DisplayFeed
     */
    public DisplayFeed build(Feed feed, List<FeedReply> feedReplies) {
        if (feed == null || feed.isDeleted()) {
            return DisplayFeed.empty();
        }

        DisplayFeedAction likedFeedAction = displayFeedActionBuilder.buildLikeBy(feed);
        DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.buildClaimBy(feed);

        User writer = userService.findByUserId(feed.getUserId());
        List<DisplayFeedReply> displayFeedReplies = displayFeedReplyBuilder.build(feedReplies);

        DisplayFeed displayFeed = new DisplayFeed(feed, writer, isWriter(writer));
        displayFeed.setLikeFeedAction(likedFeedAction);
        displayFeed.setClaimFeedAction(claimedFeedAction);
        displayFeed.setReplies(displayFeedReplies);
        return displayFeed;
    }

    private Boolean isWriter(User writer) {
        User loginUser = loggedUser.get();
        return writer.isMatchUser(loginUser.getUserId());
    }
}
