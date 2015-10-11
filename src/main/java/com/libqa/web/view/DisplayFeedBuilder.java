package com.libqa.web.view;

import com.google.common.collect.Lists;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.service.FeedActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class DisplayFeedBuilder {
    private static final String DATE_FORAMT = "yyyy/MM/dd";

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedActionService feedActionService;

    public DisplayFeedAction buildFeedAction(Integer actionCount, boolean hasViewer) {
        return new DisplayFeedAction(actionCount, hasViewer);
    }

    public List<DisplayFeed> buildFeeds(List<Feed> feeds) {
        User user = loggedUser.getDummyUser();
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        for (Feed feed : feeds) {
            DisplayFeedAction likedFeedAction = buildFeedAction(feed.getLikeCount(), feedActionService.hasLike(feed, user));
            DisplayFeedAction claimedFeedAction = buildFeedAction(feed.getClaimCount(), feedActionService.hasClaim(feed, user));
            displayFeeds.add(buildFeed(feed, likedFeedAction, claimedFeedAction));
        }
        return displayFeeds;
    }

    private List<DisplayFeedReply> buildFeedReplies(List<FeedReply> feedReplies) {
        User user = loggedUser.getDummyUser();
        List<DisplayFeedReply> displayFeedReplies = Lists.newArrayList();
        for (FeedReply feedReply : feedReplies) {
            DisplayFeedAction likedFeedAction = buildFeedAction(feedReply.getLikeCount(), feedActionService.hasLike(feedReply, user));
            DisplayFeedAction claimedFeedAction = buildFeedAction(feedReply.getClaimCount(), feedActionService.hasClaim(feedReply, user));
            displayFeedReplies.add(buildFeedReply(feedReply, likedFeedAction, claimedFeedAction));
        }
        return displayFeedReplies;
    }

    private DisplayFeed buildFeed(Feed feed, DisplayFeedAction likedFeedAction, DisplayFeedAction claimedFeedAction) {
        return new DisplayFeed(feed, likedFeedAction, claimedFeedAction, buildFeedReplies(feed.getFeedReplies()));
    }

    private DisplayFeedReply buildFeedReply(FeedReply feedReply, DisplayFeedAction likedFeedAction, DisplayFeedAction claimedFeedAction) {
        return new DisplayFeedReply(feedReply, likedFeedAction, claimedFeedAction);
    }

    public static String toDisplayDate(Date date) {
        return new SimpleDateFormat(DATE_FORAMT).format(date);
    }

}
