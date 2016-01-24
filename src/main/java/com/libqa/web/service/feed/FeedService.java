package com.libqa.web.service.feed;

import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.*;
import com.libqa.web.repository.FeedFileRepository;
import com.libqa.web.repository.FeedReplyRepository;
import com.libqa.web.repository.FeedRepository;
import com.libqa.web.service.feed.actor.FeedClaim;
import com.libqa.web.service.feed.actor.FeedLike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
public class FeedService {
    private static final Sort DEFAULT_SORT = new Sort(DESC, "feedId");

    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private FeedFileService feedFileService;
    @Autowired
    private FeedReplyRepository feedReplyRepository;
    @Autowired
    private FeedFileRepository feedFileRepository;
    @Autowired
    private FeedActionService feedActionService;

    /**
     * lastFeedId을 기준으로 feed 목록을 조회한다.
     * lastFeedId가 null일 경우 가장 최근의 목록을 반환하게 된다.
     *
     * @param lastFeedId
     * @return List&lt;Feed&gt;
     */
    public List<Feed> search(Integer lastFeedId) {
        PageRequest pageRequest = PageUtil.sortPageable(DEFAULT_SORT);

        Optional<Integer> lastFeedIdOptional = Optional.ofNullable(lastFeedId);
        if (lastFeedIdOptional.isPresent()) {
            return feedRepository.findByFeedIdLessThanAndIsDeletedFalse(lastFeedId, pageRequest);
        }
        return feedRepository.findByIsDeletedFalse(pageRequest);
    }

    /**
     * user를 기반으로 feed를 저장한다.
     *
     * @param feed
     * @param user
     */
    @Transactional
    public void save(Feed feed, User user) {
        feed.setUserNick(user.getUserNick());
        feed.setUserId(user.getUserId());
        feed.setInsertUserId(user.getUserId());
        feed.setInsertDate(new Date());
        feedRepository.save(feed);
        saveFeedFiles(feed);
    }

    /**
     * feed 삭제한다. reply/file 정보도 함께 제거한다.
     *
     * @param feed
     */
    @Transactional
    public void delete(Feed feed) {
        feed.setDeleted(true);

        List<FeedReply> feedReplies = feedReplyRepository.findByFeedFeedId(feed.getFeedId());
        for (FeedReply each : feedReplies) {
            each.setDeleted(true);
        }

        List<FeedFile> feedFiles = feedFileRepository.findByFeedFeedId(feed.getFeedId());
        for (FeedFile each : feedFiles) {
            each.setDeleted(true);
        }
    }

    /**
     * user 기반으로 좋아요를 처리한다.
     *
     * @param feedId
     * @param user
     * @return Feed
     */
    @Transactional
    public Feed like(Integer feedId, User user) {
        Feed feed = feedRepository.findOne(feedId);
        FeedLike feedLike = FeedLike.of(feed.getFeedId());
        FeedAction feedAction = feedActionService.getFeedActionByUser(user, feedLike);
        if (feedAction.isNotYet()) {
            feedActionService.create(user, feedLike);
        } else {
            feedAction.cancel();
        }
        feed.setLikeCount(feedActionService.getCount(feedLike));
        return feed;
    }

    /**
     * user 기반으로 claim을 처리한다.
     *
     * @param feedId
     * @param user
     * @return Feed
     */
    @Transactional
    public Feed claim(Integer feedId, User user) {
        Feed feed = feedRepository.findOne(feedId);
        FeedClaim feedClaim = FeedClaim.of(feed.getFeedId());
        FeedAction feedAction = feedActionService.getFeedActionByUser(user, feedClaim);
        if (feedAction.isNotYet()) {
            feedActionService.create(user, feedClaim);
        } else {
            feedAction.cancel();
        }
        feed.setClaimCount(feedActionService.getCount(feedClaim));
        return feed;
    }

    /**
     * feed를 수정한다.
     *
     * @param originFeed
     * @param requestFeed
     * @return Feed
     */
    @Transactional
    public Feed modify(Feed originFeed, Feed requestFeed) {
        originFeed.setFeedContent(requestFeed.getFeedContent());
        return originFeed;
    }

    /**
     * feedId로 feed를 조회한다.
     *
     * @param feedId
     * @return Feed
     */
    public Feed findByFeedId(Integer feedId) {
        return feedRepository.findOne(feedId);
    }

    /**
     * pageSize만큼 최신 feed 목록을 조회한다.
     *
     * @param pageSize
     * @return Feed
     */
    public List<Feed> searchRecentlyFeedsByPageSize(Integer pageSize) {
        return feedRepository.findByIsDeletedFalse(PageUtil.sortPageable(pageSize, DEFAULT_SORT));
    }

    /**
     * userId로 feed 목록을 조회한다.
     *
     * @param userId
     * @param lastFeedId
     * @return List&lt;Feed&gt;
     */
    public List<Feed> searchByUserId(Integer userId, Integer lastFeedId) {
        PageRequest pageRequest = PageUtil.sortPageable(DEFAULT_SORT);

        Optional<Integer> lastFeedIdOptional = Optional.ofNullable(lastFeedId);
        if (lastFeedIdOptional.isPresent()) {
            return feedRepository.findByUserIdAndFeedIdLessThanAndIsDeletedFalse(userId, lastFeedId, pageRequest);
        }
        return feedRepository.findByUserIdAndIsDeletedFalse(userId, pageRequest);
    }

    private void saveFeedFiles(Feed feed) {
        Optional.ofNullable(feed.getFeedFiles()).ifPresent(list -> list.forEach(
                feedFile -> {
                    feedFile.setUserNick(feed.getUserNick());
                    feedFile.setUserId(feed.getUserId());
                    feedFile.setInsertUserId(feed.getInsertUserId());
                    feedFile.setInsertDate(new Date());
                    feedFile.setFeed(feed);
                    feedFileService.save(feedFile);
                }
        ));
    }

}
