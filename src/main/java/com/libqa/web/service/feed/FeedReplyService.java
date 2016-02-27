package com.libqa.web.service.feed;

import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedReplyRepository;
import com.libqa.web.service.feed.actor.FeedReplyClaim;
import com.libqa.web.service.feed.actor.FeedReplyLike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class FeedReplyService {

    @Autowired
    private FeedThreadService feedThreadService;
    @Autowired
    private FeedReplyRepository feedReplyRepository;
    @Autowired
    private FeedActionService feedActionService;

    /**
     * user 기반으로 feed 댓글을 저장한다.
     *
     * @param feedReply
     * @param user
     */
    @Transactional
    public void create(FeedReply feedReply, User user) {
        feedReply.setUserId(user.getUserId());
        feedReply.setUserNick(user.getUserNick());
        feedReply.setInsertUserId(user.getUserId());
        feedReply.setDeleted(false);
        feedReply.setInsertDate(new Date());
        feedReplyRepository.save(feedReply);
        updateFeedReplyCount(feedReply);
    }

    /**
     * feedReplyId로 댓글을 삭제한다.
     *
     * @param feedReplyId
     */
    @Transactional
    public void delete(Integer feedReplyId) {
        FeedReply feedReply = feedReplyRepository.findOne(feedReplyId);
        feedReply.setDeleted(true);
        feedReplyRepository.save(feedReply);
        updateFeedReplyCount(feedReply);
    }

    private void updateFeedReplyCount(FeedReply feedReply) {
        final Integer feedThreadId = feedReply.getFeedThread().getFeedThreadId();
        FeedThread feedThread = feedThreadService.getByFeedThreadId(feedThreadId);
        feedThread.setReplyCount(feedReplyRepository.countByFeedThreadAndIsDeletedFalse(feedThread));
    }

    /**
     * user기반으로 feed 댓글에 like를 처리한다.
     *
     * @param feedReplyId
     * @param actionUser
     * @return FeedReply
     */
    @Transactional
    public FeedReply like(Integer feedReplyId, User actionUser) {
        FeedReply feedReply = feedReplyRepository.findOne(feedReplyId);
        FeedReplyLike feedReplyLike = FeedReplyLike.of(feedReply.getFeedReplyId(), actionUser);

        feedActionService.act(feedReplyLike);
        Integer likeCount = feedActionService.countOf(feedReplyLike);

        feedReply.setLikeCount(likeCount);
        return feedReply;
    }

    /**
     * user기반으로 feed 댓글에 cliam을 처리한다.
     *
     * @param feedReplyId
     * @param actionUser
     * @return FeedReply
     */
    @Transactional
    public FeedReply claim(Integer feedReplyId, User actionUser) {
        FeedReply feedReply = feedReplyRepository.findOne(feedReplyId);
        FeedReplyClaim feedReplyClaim = FeedReplyClaim.of(feedReply.getFeedReplyId(), actionUser);

        feedActionService.act(feedReplyClaim);
        Integer claimCount = feedActionService.countOf(feedReplyClaim);

        feedReply.setClaimCount(claimCount);
        return feedReply;
    }

    /**
     * feedReplyId로 feed 댓글을 조회한다.
     *
     * @param feedReplyId
     * @return FeedReply
     */
    public FeedReply getByFeedReplyId(Integer feedReplyId) {
        return feedReplyRepository.findOne(feedReplyId);
    }
}
