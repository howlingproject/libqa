package com.libqa.web.service.feed;

import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedFileRepository;
import com.libqa.web.repository.FeedReplyRepository;
import com.libqa.web.repository.FeedThreadRepository;
import com.libqa.web.service.feed.actor.FeedClaim;
import com.libqa.web.service.feed.actor.FeedLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class FeedThreadService {
    private static final Sort DEFAULT_SORT = new Sort(DESC, "feedThreadId");

    @Autowired
    private FeedThreadRepository feedThreadRepository;
    @Autowired
    private FeedFileService feedFileService;
    @Autowired
    private FeedReplyRepository feedReplyRepository;
    @Autowired
    private FeedFileRepository feedFileRepository;
    @Autowired
    private FeedActionService feedActionService;

    /**
     * 최신 feedThread 목록을 조회한다.
     *
     * @return List&lt;FeedThread&gt;
     */
    public List<FeedThread> searchRecentlyFeeds() {
        return searchRecentlyFeedsWithlastFeedThreadId(null);
    }

    /**
     * lastFeedThreadId을 기준으로 최신 feedThread 목록을 조회한다.
     *
     * @param lastFeedThreadId
     * @return List&lt;FeedThread&gt;
     */
    public List<FeedThread> searchRecentlyFeedsWithlastFeedThreadId(Integer lastFeedThreadId) {
        PageRequest pageRequest = PageUtil.sortPageable(DEFAULT_SORT);

        Optional<Integer> lastFeedThreadIdOptional = Optional.ofNullable(lastFeedThreadId);
        if (lastFeedThreadIdOptional.isPresent()) {
            return feedThreadRepository.findByFeedThreadIdLessThanAndIsDeletedFalse(lastFeedThreadId, pageRequest);
        }
        return feedThreadRepository.findByIsDeletedFalse(pageRequest);
    }

    /**
     * pageSize만큼 최신 feedThread 목록을 조회한다.
     *
     * @param pageSize
     * @return FeedThread
     */
    public List<FeedThread> searchRecentlyFeedsByPageSize(Integer pageSize) {
        return feedThreadRepository.findByIsDeletedFalse(PageUtil.sortPageable(pageSize, DEFAULT_SORT));
    }

    /**
     * user를 기반으로 feed를 저장한다.
     *
     * @param feedThread
     * @param user
     */
    @Transactional
    public void create(FeedThread feedThread, User user) {
        feedThread.setUserNick(user.getUserNick());
        feedThread.setUserId(user.getUserId());
        feedThread.setInsertUserId(user.getUserId());
        feedThread.setInsertDate(new Date());
        feedThread.setReplyCount(0);

        final boolean hasUploadFiles = !CollectionUtils.isEmpty(feedThread.getFeedFiles());
        if (hasUploadFiles) {
            final List<FeedFile> feedFiles = feedThread.getFeedFiles();
            feedThread.setFileCount(feedFiles.size());
            feedFiles.forEach(feedFile -> {
                        feedFile.setUserNick(feedThread.getUserNick());
                        feedFile.setUserId(feedThread.getUserId());
                        feedFile.setInsertUserId(feedThread.getInsertUserId());
                        feedFile.setInsertDate(new Date());
                        feedFile.setFeedThread(feedThread);
                        feedFileService.save(feedFile);
                    }
            );
        }
        feedThreadRepository.save(feedThread);
    }

    /**
     * feedThread 삭제한다. reply/file 정보도 함께 제거한다.
     *
     * @param feedThread
     */
    @Transactional
    public void delete(FeedThread feedThread) {
        feedThread.setDeleted(true);
        feedThread.setFileCount(0);
        feedThread.setReplyCount(0);

        final Integer feedThreadId = feedThread.getFeedThreadId();
        List<FeedReply> feedReplies = feedReplyRepository.findByFeedThreadFeedThreadId(feedThreadId);
        for (FeedReply each : feedReplies) {
            each.setDeleted(true);
        }

        List<FeedFile> feedFiles = feedFileRepository.findByFeedThreadFeedThreadId(feedThreadId);
        for (FeedFile each : feedFiles) {
            each.setDeleted(true);
        }
    }

    /**
     * user 기반으로 좋아요를 처리한다.
     *
     * @param feedThreadId
     * @param actionUser
     * @return FeedThread
     */
    @Transactional
    public FeedThread like(Integer feedThreadId, User actionUser) {
        FeedThread feedThread = getByFeedThreadId(feedThreadId);
        FeedLike feedLike = FeedLike.of(feedThread.getFeedThreadId(), actionUser);

        feedActionService.act(feedLike);
        Integer likeCount = feedActionService.countOf(feedLike);
        feedThread.setLikeCount(likeCount);
        return feedThread;
    }

    /**
     * user 기반으로 claim을 처리한다.
     *
     * @param feedThreadId
     * @param actionUser
     * @return FeedThread
     */
    @Transactional
    public FeedThread claim(Integer feedThreadId, User actionUser) {
        FeedThread feedThread = getByFeedThreadId(feedThreadId);
        FeedClaim feedClaim = FeedClaim.of(feedThread.getFeedThreadId(), actionUser);

        feedActionService.act(feedClaim);
        Integer claimCount = feedActionService.countOf(feedClaim);

        feedThread.setClaimCount(claimCount);
        return feedThread;
    }

    /**
     * feed를 수정한다.
     *
     * @param originFeedThread
     * @param requestFeedThread
     * @return FeedThread
     */
    @Transactional
    public FeedThread modify(FeedThread originFeedThread, FeedThread requestFeedThread, User user) {
        originFeedThread.setFeedContent(requestFeedThread.getFeedContent());
        originFeedThread.setUpdateDate(new Date());
        originFeedThread.setUpdateUserId(user.getUserId());
        return originFeedThread;
    }

    /**
     * feedThreadId로 FeedThread 를 조회한다.
     *
     * @param feedThreadId
     * @return FeedThread
     */
    public FeedThread getByFeedThreadId(Integer feedThreadId) {
        return feedThreadRepository.findOne(feedThreadId);
    }

    /**
     * userId로 최신 feedThread 목록을 조회한다.
     *
     * @return List&lt;FeedThread&gt;
     */
    public List<FeedThread> searchRecentlyFeedsByUser(User user) {
        return searchRecentlyFeedsByUserWithlastFeedThreadId(user, null);
    }

    /**
     * userId로 feedThread 목록을 조회한다.
     *
     * @param user
     * @param lastFeedThreadId
     * @return List&lt;FeedThread&gt;
     */
    public List<FeedThread> searchRecentlyFeedsByUserWithlastFeedThreadId(User user, Integer lastFeedThreadId) {
        PageRequest pageRequest = PageUtil.sortPageable(DEFAULT_SORT);
        Optional<Integer> lastFeedThreadIdOptional = Optional.ofNullable(lastFeedThreadId);

        if (lastFeedThreadIdOptional.isPresent()) {
            return feedThreadRepository.findByUserIdAndFeedThreadIdLessThanAndIsDeletedFalse(user.getUserId(), lastFeedThreadId, pageRequest);
        }

        return feedThreadRepository.findByUserIdAndIsDeletedFalse(user.getUserId(), pageRequest);
    }

}
