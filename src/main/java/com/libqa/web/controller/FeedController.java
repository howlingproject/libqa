package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.service.FeedReplyService;
import com.libqa.web.service.FeedService;
import com.libqa.web.view.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.libqa.application.enums.FeedActionType.*;
import static com.libqa.application.framework.ResponseData.createFailResult;
import static com.libqa.application.framework.ResponseData.createSuccessResult;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedService feedService;
    @Autowired
    private FeedReplyService feedReplyService;
    @Autowired
    private DisplayFeedBuilder displayFeedBuilder;
    @Autowired
    private DisplayFeedActionBuilder displayFeedActionBuilder;

    @RequestMapping(method = GET)
    public ModelAndView main(ModelAndView mav) {
        mav.setViewName("feed/main");
        return mav;
    }

    @RequestMapping(value = "list", method = GET)
    public ResponseData<DisplayFeed> list(@RequestParam(required = false) Integer lastFeedId) {
        List<Feed> feeds = feedService.search(lastFeedId);
        return createSuccessResult(displayFeedBuilder.buildFeeds(feeds));
    }

    @RequestMapping(value = "save", method = POST)
    public ResponseData<Feed> save(Feed feed) {
        try {
            feedService.save(feed, loggedUser.getDummyUser());
            return createSuccessResult(feed);
        } catch (Exception e) {
            log.error("save feed error.", e);
            return createFailResult(feed);
        }
    }

    @RequestMapping(value = "{feedId}/delete", method = POST)
    public ResponseData<Integer> deleteByFeedId(@PathVariable Integer feedId) {
        try {
            feedService.deleteByFeedId(feedId);
            return createSuccessResult(feedId);
        } catch (Exception e) {
            log.error("delete feed error.", e);
            return createFailResult(feedId);
        }
    }

    @RequestMapping(value = "reply/{feedReplyId}/delete", method = POST)
    public ResponseData<Integer> deleteFeedReply(@PathVariable Integer feedReplyId) {
        try {
            feedReplyService.deleteByFeedReplyId(feedReplyId);
            return createSuccessResult(feedReplyId);
        } catch (Exception e) {
            log.error("delete reply error.", e);
            return createFailResult(feedReplyId);
        }
    }

    @RequestMapping(value = "reply/save", method = POST)
    public ResponseData<DisplayFeedReply> saveFeedReply(FeedReply feedReply) {
        try {
            feedReplyService.save(feedReply);
            return createSuccessResult(new DisplayFeedReply(feedReply));
        } catch (Exception e) {
            log.error("save reply error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "{feedId}/like", method = POST)
    public ResponseData<DisplayFeedAction> likeFeed(@PathVariable Integer feedId) {
        try {
            User user = loggedUser.getDummyUser();
            Feed feed = feedService.like(feedId, user);
            return createSuccessResult(displayFeedActionBuilder.build(feed.getLikeCount(), feed.getFeedId(), FEED_LIKE));
        } catch (Exception e) {
            log.error("like feed error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "{feedId}/claim", method = POST)
    public ResponseData<DisplayFeedAction> claimFeed(@PathVariable Integer feedId) {
        try {
            User user = loggedUser.getDummyUser();
            Feed feed = feedService.claim(feedId, user);
            return createSuccessResult(displayFeedActionBuilder.build(feed.getClaimCount(), feed.getFeedId(), FEED_CLAIM));
        } catch (Exception e) {
            log.error("claim feed error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "reply/{feedReplyId}/like", method = POST)
    public ResponseData<DisplayFeedAction> likeFeedReply(@PathVariable Integer feedReplyId) {
        try {
            User user = loggedUser.getDummyUser();
            FeedReply feedReply = feedReplyService.like(feedReplyId, user);
            return createSuccessResult(displayFeedActionBuilder.build(feedReply.getLikeCount(), feedReply.getFeedReplyId(), FEED_REPLY_LIKE));
        } catch (Exception e) {
            log.error("like feedReply error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "reply/{feedReplyId}/claim", method = POST)
    public ResponseData<DisplayFeedAction> claimFeedReply(@PathVariable Integer feedReplyId) {
        try {
            User user = loggedUser.getDummyUser();
            FeedReply feedReply = feedReplyService.claim(feedReplyId, user);
            return createSuccessResult(displayFeedActionBuilder.build(feedReply.getClaimCount(), feedReply.getFeedReplyId(), FEED_REPLY_CLAIM));
        } catch (Exception e) {
            log.error("claim feedReply error.", e);
            return createFailResult(null);
        }
    }
}
