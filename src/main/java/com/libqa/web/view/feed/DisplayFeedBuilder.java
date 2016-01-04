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

@Component
public class DisplayFeedBuilder {
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private UserService userService;
    @Autowired
    private DisplayFeedActionBuilder displayFeedActionBuilder;

    /**
     * feed를 display 용으로 build 한다.
     * @param feeds
     * @return
     */
    public List<DisplayFeed> buildFeeds(List<Feed> feeds) {
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        for (Feed feed : feeds) {
            DisplayFeedAction likedFeedAction = displayFeedActionBuilder.buildLikeBy(feed);
            DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.buildClaimBy(feed);
            displayFeeds.add(buildFeed(feed, likedFeedAction, claimedFeedAction));
        }
        return displayFeeds;
    }

    private DisplayFeed buildFeed(Feed feed, DisplayFeedAction likedFeedAction, DisplayFeedAction claimedFeedAction) {
        User writer = userService.findByUserId(feed.getUserId());
        List<DisplayFeedReply> displayFeedReplies = buildFeedReplies(feed.getFeedReplies());

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

    private List<DisplayFeedReply> buildFeedReplies(List<FeedReply> feedReplies) {
        List<DisplayFeedReply> displayFeedReplies = Lists.newArrayList();
        for (FeedReply feedReply : feedReplies) {
            DisplayFeedAction likedFeedAction = displayFeedActionBuilder.buildLikeBy(feedReply);
            DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.buildClaimBy(feedReply);
            displayFeedReplies.add(buildFeedReply(feedReply, likedFeedAction, claimedFeedAction));
        }
        return displayFeedReplies;
    }

    private DisplayFeedReply buildFeedReply(FeedReply feedReply, DisplayFeedAction likedFeedAction, DisplayFeedAction claimedFeedAction) {
        User writer = userService.findByUserId(feedReply.getUserId());

        DisplayFeedReply displayFeedReply = new DisplayFeedReply(feedReply, writer, isWriter(writer));
        displayFeedReply.setLikeFeedAction(likedFeedAction);
        displayFeedReply.setClaimFeedAction(claimedFeedAction);
        return displayFeedReply;
    }

}
