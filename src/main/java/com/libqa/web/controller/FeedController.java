package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.service.feed.FeedReplyService;
import com.libqa.web.service.feed.FeedService;
import com.libqa.web.view.DisplayFeed;
import com.libqa.web.view.feed.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.libqa.application.enums.StatusCode.NEED_LOGIN;
import static com.libqa.application.enums.StatusCode.NOT_MATCH_USER;
import static com.libqa.application.framework.ResponseData.*;
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
        mav.addObject("loggedUser", loggedUser.get());
        return mav;
    }

    @RequestMapping(value = "list", method = GET)
    public ResponseData<DisplayFeed> list(@RequestParam(required = false) Integer lastFeedId) {
        List<Feed> feeds = feedService.search(lastFeedId);
        return createSuccessResult(displayFeedBuilder.build(feeds));
    }

    @RequestMapping(value = "save", method = POST)
    public ResponseData<Feed> save(Feed feed) {
        User user = loggedUser.get();
        if (user.isGuest()) {
            return createResult(NEED_LOGIN);
        }

        try {
            feedService.save(feed, user);
            return createSuccessResult(feed);
        } catch (Exception e) {
            log.error("save feed error.", e);
            return createFailResult(feed);
        }
    }

    @RequestMapping(value = "/modify", method = POST)
    public ResponseData<Feed> modify(Feed requestFeed) {
        User user = loggedUser.get();
        try {
            log.debug("requestFeed : {}", requestFeed);
            Feed originFeed = feedService.findByFeedId(requestFeed.getFeedId());
            if (user.isNotMatchUser(originFeed.getUserId())) {
                return createResult(NOT_MATCH_USER);
            }

            Feed savedFeed = feedService.modify(originFeed, requestFeed);
            return createSuccessResult(savedFeed);
        } catch (Exception e) {
            log.error("delete feed error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "{feedId}/delete", method = POST)
    public ResponseData<Integer> delete(@PathVariable Integer feedId) {
        User user = loggedUser.get();
        try {
            Feed originFeed = feedService.findByFeedId(feedId);
            if (user.isNotMatchUser(originFeed.getUserId())) {
                return createResult(NOT_MATCH_USER);
            }

            feedService.delete(originFeed);
            return createSuccessResult(feedId);
        } catch (Exception e) {
            log.error("delete feed error.", e);
            return createFailResult(feedId);
        }
    }

    @RequestMapping(value = "{feedId}/like", method = POST)
    public ResponseData<DisplayFeedAction> likeFeed(@PathVariable Integer feedId) {
        User user = loggedUser.get();
        if (user.isGuest()) {
            return createResult(NEED_LOGIN);
        }

        try {
            Feed feed = feedService.like(feedId, user);
            DisplayFeedAction displayFeedAction = displayFeedActionBuilder.buildLikeBy(feed);
            return createSuccessResult(displayFeedAction);
        } catch (Exception e) {
            log.error("like feed error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "{feedId}/claim", method = POST)
    public ResponseData<DisplayFeedAction> claimFeed(@PathVariable Integer feedId) {
        User user = loggedUser.get();
        if (user.isGuest()) {
            return createResult(NEED_LOGIN);
        }

        try {
            Feed feed = feedService.claim(feedId, user);
            DisplayFeedAction displayFeedAction = displayFeedActionBuilder.buildClaimBy(feed);
            return createSuccessResult(displayFeedAction);
        } catch (Exception e) {
            log.error("claim feed error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "reply/save", method = POST)
    public ResponseData<DisplayFeedReply> saveReply(FeedReply feedReply) {
        User user = loggedUser.get();
        if (user.isGuest()) {
            return createResult(NEED_LOGIN);
        }

        try {
            feedReplyService.save(feedReply, user);
            return createSuccessResult(new DisplayFeedReply(feedReply));
        } catch (Exception e) {
            log.error("save reply error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "reply/{feedReplyId}/delete", method = POST)
    public ResponseData<Integer> deleteReply(@PathVariable Integer feedReplyId) {
        User user = loggedUser.get();

        try {
            FeedReply originFeedReply = feedReplyService.findByFeedReplyId(feedReplyId);
            if (user.isNotMatchUser(originFeedReply.getUserId())) {
                return createResult(NOT_MATCH_USER);
            }

            feedReplyService.deleteByFeedReplyId(feedReplyId);
            return createSuccessResult(feedReplyId);
        } catch (Exception e) {
            log.error("delete reply error.", e);
            return createFailResult(feedReplyId);
        }
    }

    @RequestMapping(value = "reply/{feedReplyId}/like", method = POST)
    public ResponseData<DisplayFeedAction> likeReply(@PathVariable Integer feedReplyId) {
        User user = loggedUser.get();
        if (user.isGuest()) {
            return createResult(NEED_LOGIN);
        }

        try {
            FeedReply feedReply = feedReplyService.like(feedReplyId, user);
            DisplayFeedAction displayFeedAction = displayFeedActionBuilder.buildLikeBy(feedReply);
            return createSuccessResult(displayFeedAction);
        } catch (Exception e) {
            log.error("like feedReply error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "reply/{feedReplyId}/claim", method = POST)
    public ResponseData<DisplayFeedAction> claimReply(@PathVariable Integer feedReplyId) {
        User user = loggedUser.get();
        if (user.isGuest()) {
            return createResult(NEED_LOGIN);
        }

        try {
            FeedReply feedReply = feedReplyService.claim(feedReplyId, user);
            DisplayFeedAction displayFeedAction = displayFeedActionBuilder.buildClaimBy(feedReply);
            return createSuccessResult(displayFeedAction);
        } catch (Exception e) {
            log.error("claim feedReply error.", e);
            return createFailResult(null);
        }
    }
}
