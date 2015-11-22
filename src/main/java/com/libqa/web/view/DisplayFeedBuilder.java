package com.libqa.web.view;

import com.google.common.collect.Lists;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DisplayFeedBuilder {

    @Autowired
    private DisplayFeedActionBuilder displayFeedActionBuilder;

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
        return new DisplayFeed(feed, likedFeedAction, claimedFeedAction, buildFeedReplies(feed.getFeedReplies()));
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
        return new DisplayFeedReply(feedReply, likedFeedAction, claimedFeedAction);
    }

}
