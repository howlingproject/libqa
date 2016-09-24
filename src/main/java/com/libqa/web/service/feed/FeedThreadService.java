package com.libqa.web.service.feed;

import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedFileRepository;
import com.libqa.web.repository.FeedReplyRepository;
import com.libqa.web.repository.FeedThreadRepository;
import com.libqa.web.service.feed.actor.FeedThreadClaim;
import com.libqa.web.service.feed.actor.FeedThreadLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

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
    public List<FeedThread> searchRecentlyFeedThreads() {
        PageRequest pageRequest = PageUtil.sortPageable(DEFAULT_SORT);
        return feedThreadRepository.findByIsDeletedFalse(pageRequest);
    }

    /**
     * lastId을 보다 작은 최신 feedThread 목록을 조회한다.
     *
     * @param lastId
     * @return List&lt;FeedThread&gt;
     */
    public List<FeedThread> searchRecentlyFeedThreadsLessThanLastId(Integer lastId) {
        PageRequest pageRequest = PageUtil.sortPageable(DEFAULT_SORT);
        return feedThreadRepository.findByFeedThreadIdLessThanAndIsDeletedFalse(lastId, pageRequest);
    }

    /**
     * pageSize만큼 최신 feedThread 목록을 조회한다.
     *
     * @param pageSize
     * @return FeedThread
     */
    public List<FeedThread> searchRecentlyFeedThreadsByPageSize(Integer pageSize) {
        PageRequest pageRequest = PageUtil.sortPageable(pageSize, DEFAULT_SORT);
        return feedThreadRepository.findByIsDeletedFalse(pageRequest);
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
        FeedThreadLike feedThreadLike = FeedThreadLike.of(feedThread.getFeedThreadId(), actionUser);

        feedActionService.act(feedThreadLike);
        Integer likeCount = feedActionService.countOf(feedThreadLike);
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
        FeedThreadClaim feedThreadClaim = FeedThreadClaim.of(feedThread.getFeedThreadId(), actionUser);

        feedActionService.act(feedThreadClaim);
        Integer claimCount = feedActionService.countOf(feedThreadClaim);

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
    public List<FeedThread> searchRecentlyFeedThreadsByUser(User user) {
        PageRequest pageRequest = PageUtil.sortPageable(DEFAULT_SORT);
        return feedThreadRepository.findByUserIdAndIsDeletedFalse(user.getUserId(), pageRequest);
    }

    /**
     * userId로 lastId보다 작은 최신 feedThread 목록을 조회한다.
     *
     * @param user
     * @param lastId
     * @return List&lt;FeedThread&gt;
     */
    public List<FeedThread> searchRecentlyFeedThreadsByUserLessThanLastId(User user, Integer lastId) {
        PageRequest pageRequest = PageUtil.sortPageable(DEFAULT_SORT);
        return feedThreadRepository.findByUserIdAndFeedThreadIdLessThanAndIsDeletedFalse(
                user.getUserId(), lastId, pageRequest);
    }

}
