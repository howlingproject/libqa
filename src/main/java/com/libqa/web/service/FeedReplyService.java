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

@Service
public class FeedReplyService {

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedActionService feedActionService;
    @Autowired
    private FeedReplyRepository feedReplyRepository;

    public void save(FeedReply feedReply) {
        User user = loggedUser.getDummyUser(); // TODO fix to realuser
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
        FeedAction feedAction = feedActionService.getLiked(feedReply, user);
        if (feedAction == null) {
            feedActionService.newLike(feedReply, user);
        } else {
            feedAction.cancel();
        }
        feedReply.setLikeCount(feedActionService.getLikeCount(feedReply));
        return feedReply;
    }

    @Transactional
    public FeedReply claim(Integer feedReplyId, User user) {
        FeedReply feedReply = feedReplyRepository.findOne(feedReplyId);
        FeedAction feedAction = feedActionService.getClaimed(feedReply, user);
        if (feedAction == null) {
            feedActionService.newClaim(feedReply, user);
        } else {
            feedAction.cancel();
        }
        feedReply.setClaimCount(feedActionService.getClaimCount(feedReply));
        return feedReply;
    }

}
