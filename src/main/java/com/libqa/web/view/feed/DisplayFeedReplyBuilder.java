package com.libqa.web.view.feed;

import com.google.common.collect.Lists;
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

    public List<DisplayFeedReply> build(List<FeedReply> feedReplies, User viewer) {
        List<DisplayFeedReply> displayFeedReplies = Lists.newArrayList();
        displayFeedReplies.addAll(feedReplies.stream()
                .map(feedReply -> build(feedReply, viewer))
                .collect(Collectors.toList())
        );
        return displayFeedReplies;
    }

    private DisplayFeedReply build(FeedReply feedReply, User viewer) {
        User writer = userService.findByUserId(feedReply.getUserId());
        DisplayFeedAction likedFeedAction = displayFeedActionBuilder.buildLikeBy(feedReply, viewer);
        DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.buildClaimBy(feedReply, viewer);

        final boolean isWriter = writer.isMatchUser(viewer.getUserId());

        DisplayFeedReply displayFeedReply = new DisplayFeedReply(feedReply, writer, isWriter);
        displayFeedReply.setLikeFeedAction(likedFeedAction);
        displayFeedReply.setClaimFeedAction(claimedFeedAction);
        return displayFeedReply;
    }
}
