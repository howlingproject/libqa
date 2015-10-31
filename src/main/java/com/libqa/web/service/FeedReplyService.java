package com.libqa.web.service;

import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.libqa.application.enums.FeedActionType.FEED_REPLY_CLAIM;
import static com.libqa.application.enums.FeedActionType.FEED_REPLY_LIKE;

@Service
public class FeedReplyService {

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedActionService feedActionService;
    @Autowired
    private FeedReplyRepository feedReplyRepository;

    public void save(FeedReply feedReply) {
        User user = loggedUser.getDummyUser();
        feedReply.setUserId(user.getUserId());
        feedReply.setUserNick(user.getUserNick());
        feedReply.setInsertUserId(user.getUserId());
        feedReply.setInsertDate(new Date());
        feedReplyRepository.save(feedReply);
    }

    @Transactional
    public void deleteByFeedReplyId(Integer feedReplyId) {
        FeedReply feedReply = feedReplyRepository.findOne(feedReplyId);
        feedReply.setDeleted(true);
    }

    @Transactional
    public FeedReply like(Integer feedReplyId, User user) {
        FeedReply feedReply = feedReplyRepository.findOne(feedReplyId);
        FeedAction feedAction = feedActionService.getFeedAction(feedReply.getFeedReplyId(), user.getUserId(), FEED_REPLY_LIKE);
        if (feedAction.isNotYet()) {
            feedActionService.create(feedReply.getFeedReplyId(), user.getUserId(), user.getUserNick(), FEED_REPLY_LIKE);
        } else {
            feedAction.cancel();
        }
        feedReply.setLikeCount(feedActionService.getCount(feedReply.getFeedReplyId(), FEED_REPLY_LIKE));
        return feedReply;
    }

    @Transactional
    public FeedReply claim(Integer feedReplyId, User user) {
        FeedReply feedReply = feedReplyRepository.findOne(feedReplyId);
        FeedAction feedAction = feedActionService.getFeedAction(feedReply.getFeedReplyId(), user.getUserId(), FEED_REPLY_CLAIM);
        if (feedAction.isNotYet()) {
            feedActionService.create(feedReply.getFeedReplyId(), user.getUserId(), user.getUserNick(), FEED_REPLY_CLAIM);
        } else {
            feedAction.cancel();
        }
        feedReply.setClaimCount(feedActionService.getCount(feedReply.getFeedReplyId(), FEED_REPLY_CLAIM));
        return feedReply;
    }

}
