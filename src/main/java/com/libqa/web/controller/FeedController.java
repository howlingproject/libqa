package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.service.FeedReplyService;
import com.libqa.web.service.FeedService;
import com.libqa.web.view.DisplayFeed;
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
            log.error("save feed error : {}", e);
            return createFailResult(feed);
        }
    }

    @RequestMapping(value = "reply/save", method = POST)
    public ResponseData<FeedReply> saveReply(FeedReply feedReply) {
        try {
            feedReplyService.save(feedReply);
            return createSuccessResult(feedReply);
        } catch (Exception e) {
            log.error("save reply error : {}", e);
            return createFailResult(feedReply);
        }
    }

    @RequestMapping(value = "delete/{feedId}", method = POST)
    public ResponseData<Long> delete(@PathVariable Long feedId) {
        try {
            feedService.delete(feedId);
            return createSuccessResult(feedId);
        } catch (Exception e) {
            log.error("save reply error : {}", e);
            return createFailResult(feedId);
        }
    }

    @RequestMapping(value = "reply/delete/{feedReplyId}", method = POST)
    public ResponseData<Long> deleteReply(@PathVariable Long feedReplyId) {
        try {
            feedReplyService.delete(feedReplyId);
            return createSuccessResult(feedReplyId);
        } catch (Exception e) {
            log.error("save reply error : {}", e);
            return createFailResult(feedReplyId);
        }
    }

    @RequestMapping(value = "like/{feedId}", method = GET)
    public ResponseData<Long> like(@PathVariable long feedId) {
        try {
            Integer userId = 1234; // TODO 로그인 처리
            Integer likeCount = feedService.likeOrUnlike(feedId, userId);
            return createSuccessResult(likeCount);
        } catch (Exception e) {
            log.error("like feed error : {}", e);
            return createFailResult(-1L);
        }
    }

}
