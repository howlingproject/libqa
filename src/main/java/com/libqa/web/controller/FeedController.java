package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.service.FeedReplyService;
import com.libqa.web.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;

import static com.libqa.application.framework.ResponseData.createFailResult;
import static com.libqa.application.framework.ResponseData.createSuccessResult;

@Slf4j
@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;
    @Autowired
    private FeedReplyService feedReplyService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView main(ModelAndView mav) {
        mav.setViewName("feed/main");
        return mav;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseData<Feed> list() throws IOException {
        return createSuccessResult(feedService.search(0, 10));
    }
    
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseData<Feed> save(Feed feed) {
        feed.setInsertDate(new Date());
        feed.setUserId(1234);
        feed.setUserNick("feedTester");
        feed.setInsertUserId(1234);
        try {
            feedService.save(feed);
            return createSuccessResult(feed);
        } catch (Exception e)  {
            log.error("save feed error : {}", e);
            return createFailResult(feed);
        }
    }
    
    @RequestMapping(value = "reply/save", method = RequestMethod.POST)
    public ResponseData<FeedReply> saveReply(FeedReply feedReply) {
        try {
            feedReply.setInsertDate(new Date());
            feedReply.setUserId(1234);
            feedReply.setUserNick("feedReplyTester");
            feedReply.setInsertUserId(1234);
            feedReplyService.save(feedReply);
            return createSuccessResult(feedReply);
        } catch (Exception e)  {
            log.error("save reply error : {}", e);
            return createFailResult(feedReply);
        }
    }
}
