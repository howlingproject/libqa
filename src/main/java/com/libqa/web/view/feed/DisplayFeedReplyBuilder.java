package com.libqa.web.view.feed;

import com.google.common.collect.Lists;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.FeedThread;
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
     * @param feedThread
     * @param viewer
     * @return list of DisplayFeedReply
     */
    List<DisplayFeedReply> build(FeedThread feedThread, User viewer) {
        List<DisplayFeedReply> displayFeedReplies = Lists.newArrayList();
        if (feedThread.notHasReplies()) {
            return displayFeedReplies;
        }

        displayFeedReplies.addAll(feedThread.getFeedReplies().stream()
                .map(feedReply -> build(feedReply, viewer))
                .collect(Collectors.toList())
        );
        return displayFeedReplies;
    }

    /**
     * display용 feedReply를 build 한다.
     *
     * @param feedReply
     * @return DisplayFeedReply
     */
    public DisplayFeedReply build(FeedReply feedReply) {
        return new DisplayFeedReply(feedReply);
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
