package com.libqa.web.view;

import com.google.common.collect.Lists;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.service.FeedActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DisplayFeedConverter {      
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedActionService feedActionService;
    
    public List<DisplayFeed> toDisplayFeed(List<Feed> feeds) {     
        User user = loggedUser.getDummyUser(); // TODO fix to realuser
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        for (Feed feed : feeds) {
            boolean hasLike = feedActionService.hasLike(feed, user);
            boolean hasClaim = feedActionService.hasClaim(feed, user);
            
            DisplayFeedAction likedFeedAction = toFeedAction(feed.getLikeCount(), hasLike);
            DisplayFeedAction claimedFeedAction = toFeedAction(feed.getClaimCount(), hasClaim);
            displayFeeds.add(new DisplayFeed(feed, likedFeedAction, claimedFeedAction, toDisplayFeedReplies(feed.getFeedId(), feed.getFeedReplies())));
        }
        return displayFeeds;
    }

    public DisplayFeedAction toFeedAction(Integer count, boolean hasViewerAction) {
        return new DisplayFeedAction(count, hasViewerAction);
    }

    private List<DisplayFeedReply> toDisplayFeedReplies(Integer feedId, List<FeedReply> feedReplies) {
        User user = loggedUser.getDummyUser(); // TODO fix to realuser
        List<DisplayFeedReply> displayFeedReplies = Lists.newArrayList();
        for (FeedReply feedReply : feedReplies) {
            boolean hasLike = feedActionService.hasLike(feedReply, user);
            boolean hasClaim = feedActionService.hasClaim(feedReply, user);

            DisplayFeedAction likedFeedAction = toFeedAction(feedReply.getLikeCount(), hasLike);
            DisplayFeedAction claimedFeedAction = toFeedAction(feedReply.getClaimCount(), hasClaim);
            displayFeedReplies.add(new DisplayFeedReply(feedId, feedReply, likedFeedAction, claimedFeedAction));
        }
        return displayFeedReplies;
    }
    
}
