package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.service.FeedReplyService;
import com.libqa.web.service.FeedService;
import com.libqa.web.view.DisplayFeed;
import com.libqa.web.view.DisplayFeedAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static com.libqa.application.framework.ResponseData.createFailResult;
import static com.libqa.application.framework.ResponseData.createSuccessResult;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;
    @Autowired
    private FeedReplyService feedReplyService;

    @RequestMapping(method = GET)
    public ModelAndView main(ModelAndView mav) {
        mav.setViewName("feed/main");
        return mav;
    }

    @RequestMapping(value = "list", method = GET)
    public ResponseData<DisplayFeed> list() {
        return createSuccessResult(feedService.search(0, 10));
    }

    @RequestMapping(value = "save", method = POST)
    public ResponseData<Feed> save(Feed feed) {
        try {
            feedService.save(feed);
            return createSuccessResult(feed);
        } catch (Exception e) {
            log.error("save feed error.", e);
            return createFailResult(feed);
        }
    }

    @RequestMapping(value = "reply/save", method = POST)
    public ResponseData<FeedReply> saveReply(FeedReply feedReply) {
        try {
            feedReplyService.save(feedReply);
            return createSuccessResult(feedReply);
        } catch (Exception e) {
            log.error("save reply error.", e);
            return createFailResult(feedReply);
        }
    }

    @RequestMapping(value = "{feedId}/delete", method = POST)
    public ResponseData<Integer> delete(@PathVariable Integer feedId) {
        try {
            feedService.delete(feedId);
            return createSuccessResult(feedId);
        } catch (Exception e) {
            log.error("save reply error.", e);
            return createFailResult(feedId);
        }
    }

    @RequestMapping(value = "reply/{feedReplyId}/delete", method = POST)
    public ResponseData<Integer> deleteReply(@PathVariable Integer feedReplyId) {
        try {
            feedReplyService.delete(feedReplyId);
            return createSuccessResult(feedReplyId);
        } catch (Exception e) {
            log.error("save reply error.", e);
            return createFailResult(feedReplyId);
        }
    }

    @RequestMapping(value = "{feedId}/like", method = POST)
    public ResponseData<DisplayFeedAction> likeFeed(@PathVariable Integer feedId) {
        try {
            FeedAction feedAction = feedService.like(feedId);
            Integer likeCount = feedService.getLikeCount(feedId);
            return createSuccessResult(new DisplayFeedAction(likeCount, feedAction.isNotCanceled()));
        } catch (Exception e) {
            log.error("like feed error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "{feedId}/claim", method = POST)
    public ResponseData<DisplayFeedAction> claimFeed(@PathVariable Integer feedId) {
        try {
            FeedAction feedAction = feedService.claim(feedId);
            Integer claimCount = feedService.getClaimCount(feedId);
            return createSuccessResult(new DisplayFeedAction(claimCount, feedAction.isNotCanceled()));
        } catch (Exception e) {
            log.error("claim feed error.", e);
            return createFailResult(null);
        }
    }
    
    @RequestMapping(value = "reply/{feedReplyId}/like", method = POST)
    public ResponseData<DisplayFeedAction> likeFeedReply(@PathVariable Integer feedReplyId) {
        try {
            FeedAction feedAction = feedReplyService.like(feedReplyId);
            Integer likeCount = feedReplyService.getLikeCount(feedReplyId);
            return createSuccessResult(new DisplayFeedAction(likeCount, feedAction.isNotCanceled()));
        } catch (Exception e) {
            log.error("like feedReply error.", e);
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "reply/{feedReplyId}/claim", method = POST)
    public ResponseData<DisplayFeedAction> claimFeedReply(@PathVariable Integer feedReplyId) {
        try {
            FeedAction feedAction = feedReplyService.claim(feedReplyId);
            Integer claimCount = feedReplyService.getClaimCount(feedReplyId);
            return createSuccessResult(new DisplayFeedAction(claimCount, feedAction.isNotCanceled()));
        } catch (Exception e) {
            log.error("claim feedReply error.", e);
            return createFailResult(null);
        }
    }

}
