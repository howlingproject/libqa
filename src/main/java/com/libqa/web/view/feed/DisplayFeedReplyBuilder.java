package com.libqa.web.view.feed;

import com.google.common.collect.Lists;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisplayFeedReplyBuilder {

    @Autowired
    private UserService userService;
    @Autowired
    private DisplayFeedActionBuilder displayFeedActionBuilder;

    /**
     * display용 feedReply 목록을 build 한다.
     *
     * @param feed
     * @param viewer
     * @return
     */
    List<DisplayFeedReply> build(Feed feed, User viewer) {
        List<DisplayFeedReply> displayFeedReplies = Lists.newArrayList();
        if (feed.noHasReplies()) {
            return displayFeedReplies;
        }

        displayFeedReplies.addAll(feed.getFeedReplies().stream()
                .map(feedReply -> build(feedReply, viewer))
                .collect(Collectors.toList())
        );
        return displayFeedReplies;
    }

    private DisplayFeedReply build(FeedReply feedReply, User viewer) {
        User writer = userService.findByUserId(feedReply.getUserId());
        DisplayFeedAction likedFeedAction = displayFeedActionBuilder.buildLike(feedReply, viewer);
        DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.buildClaim(feedReply, viewer);

        final boolean isWriter = writer.isMatchUser(viewer.getUserId());

        DisplayFeedReply displayFeedReply = new DisplayFeedReply(feedReply, writer, isWriter);
        displayFeedReply.setLikeFeedAction(likedFeedAction);
        displayFeedReply.setClaimFeedAction(claimedFeedAction);
        return displayFeedReply;
    }
}
