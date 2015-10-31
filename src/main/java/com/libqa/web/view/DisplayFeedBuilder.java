package com.libqa.web.view;

import com.google.common.collect.Lists;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.libqa.application.enums.FeedActionType.*;

@Component
public class DisplayFeedBuilder {
    private static final String DATE_FORAMT = "yyyy/MM/dd";

    @Autowired
    private DisplayFeedActionBuilder displayFeedActionBuilder;

    public List<DisplayFeed> buildFeeds(List<Feed> feeds) {
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        for (Feed feed : feeds) {
            DisplayFeedAction likedFeedAction = displayFeedActionBuilder.build(feed.getLikeCount(), feed.getFeedId(), FEED_LIKE);
            DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.build(feed.getClaimCount(), feed.getFeedId(), FEED_CLAIM);
            displayFeeds.add(buildFeed(feed, likedFeedAction, claimedFeedAction));
        }
        return displayFeeds;
    }

    private List<DisplayFeedReply> buildFeedReplies(List<FeedReply> feedReplies) {
        List<DisplayFeedReply> displayFeedReplies = Lists.newArrayList();
        for (FeedReply feedReply : feedReplies) {
            DisplayFeedAction likedFeedAction = displayFeedActionBuilder.build(feedReply.getLikeCount(), feedReply.getFeedReplyId(), FEED_REPLY_LIKE);
            DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.build(feedReply.getClaimCount(), feedReply.getFeedReplyId(), FEED_REPLY_CLAIM);
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
