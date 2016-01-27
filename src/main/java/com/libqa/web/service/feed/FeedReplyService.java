package com.libqa.web.service.feed;

import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedReplyRepository;
import com.libqa.web.service.feed.actor.FeedReplyClaim;
import com.libqa.web.service.feed.actor.FeedReplyLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class FeedReplyService {

    @Autowired
    private FeedActionService feedActionService;
    @Autowired
    private FeedReplyRepository feedReplyRepository;

    /**
     * user 기반으로 feed 댓글을 저장한다.
     *
     * @param feedReply
     * @param user
     */
    public void save(FeedReply feedReply, User user) {
        feedReply.setUserId(user.getUserId());
        feedReply.setUserNick(user.getUserNick());
        feedReply.setInsertUserId(user.getUserId());
        feedReply.setInsertDate(new Date());
        feedReplyRepository.save(feedReply);
    }

    /**
     * feedReplyId로 댓글을 삭제한다.
     *
     * @param feedReplyId
     */
    @Transactional
    public void deleteByFeedReplyId(Integer feedReplyId) {
        FeedReply feedReply = feedReplyRepository.findOne(feedReplyId);
        feedReply.setDeleted(true);
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
    public FeedReply findByFeedReplyId(Integer feedReplyId) {
        return feedReplyRepository.findOne(feedReplyId);
    }

    /**
     * @param feed
     * @return reply count by feed
     */
    public Integer countByFeed(Feed feed) {
        return feedReplyRepository.countByFeedId(feed.getFeedId());
    }
}
