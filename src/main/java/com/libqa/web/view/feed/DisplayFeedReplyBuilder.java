package com.libqa.web.view.feed;

import com.google.common.collect.Lists;
import com.libqa.application.util.LoggedUser;
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
    private LoggedUser loggedUser;
    @Autowired
    private UserService userService;
    @Autowired
    private DisplayFeedActionBuilder displayFeedActionBuilder;

    public List<DisplayFeedReply> build(List<FeedReply> feedReplies) {
        List<DisplayFeedReply> displayFeedReplies = Lists.newArrayList();
        displayFeedReplies.addAll(feedReplies.stream()
                .map(feedReply -> build(feedReply))
                .collect(Collectors.toList())
        );
        return displayFeedReplies;
    }

    private DisplayFeedReply build(FeedReply feedReply) {
        User writer = userService.findByUserId(feedReply.getUserId());

        DisplayFeedAction likedFeedAction = displayFeedActionBuilder.buildLikeBy(feedReply);
        DisplayFeedAction claimedFeedAction = displayFeedActionBuilder.buildClaimBy(feedReply);

        DisplayFeedReply displayFeedReply = new DisplayFeedReply(feedReply, writer, isWriter(writer));
        displayFeedReply.setLikeFeedAction(likedFeedAction);
        displayFeedReply.setClaimFeedAction(claimedFeedAction);
        return displayFeedReply;
    }

    private Boolean isWriter(User writer) {
        User loginUser = loggedUser.get();
        return writer.isMatchUser(loginUser.getUserId());
    }
}
