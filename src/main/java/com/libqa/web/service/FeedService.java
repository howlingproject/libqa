package com.libqa.web.service;

import com.libqa.application.dto.FeedRequestDto;
import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.*;
import com.libqa.web.repository.FeedFileRepository;
import com.libqa.web.repository.FeedReplyRepository;
import com.libqa.web.repository.FeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.libqa.application.enums.FeedActionType.FEED_CLAIM;
import static com.libqa.application.enums.FeedActionType.FEED_LIKE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
public class FeedService {
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

    public List<Feed> search(Integer lastFeedId) {
        Optional<Integer> lastFeedIdOptional = Optional.ofNullable(lastFeedId);
        if (lastFeedIdOptional.isPresent()) {
            return feedRepository.findByFeedIdLessThanAndIsDeletedFalse(lastFeedId, getPageRequest());
        }
        return feedRepository.findByIsDeletedFalse(getPageRequest());
    }

    @Transactional
    public void save(Feed feed, User user) {
        feed.setUserNick(user.getUserNick());
        feed.setUserId(user.getUserId());
        feed.setInsertUserId(user.getUserId());
        feed.setInsertDate(new Date());
        feedRepository.save(feed);
        saveFeedFiles(feed);
    }

    @Transactional
    public void deleteByFeedId(Integer feedId) {
        Feed feed = feedRepository.findOne(feedId);
        feed.setDeleted(true);

        List<FeedReply> feedReplies = feedReplyRepository.findByFeedFeedId(feedId);
        for (FeedReply each : feedReplies) {
            each.setDeleted(true);
        }

        List<FeedFile> feedFiles = feedFileRepository.findByFeedFeedId(feedId);
        for (FeedFile each : feedFiles) {
            each.setDeleted(true);
        }
    }

    @Transactional
    public Feed like(Integer feedId, User user) {
        Feed feed = feedRepository.findOne(feedId);
        FeedAction feedAction = feedActionService.getFeedAction(feed.getFeedId(), user.getUserId(), FEED_LIKE);
        if (feedAction.isNotYet()) {
            feedActionService.create(feed.getFeedId(), user.getUserId(), user.getUserNick(), FEED_LIKE);
        } else {
            feedAction.cancel();
        }
        feed.setLikeCount(feedActionService.getCount(feed.getFeedId(), FEED_LIKE));
        return feed;
    }

    @Transactional
    public Feed claim(Integer feedId, User user) {
        Feed feed = feedRepository.findOne(feedId);
        FeedAction feedAction = feedActionService.getFeedAction(feed.getFeedId(), user.getUserId(), FEED_CLAIM);
        if (feedAction.isNotYet()) {
            feedActionService.create(feed.getFeedId(), user.getUserId(), user.getUserNick(), FEED_CLAIM);
        } else {
            feedAction.cancel();
        }
        feed.setClaimCount(feedActionService.getCount(feed.getFeedId(), FEED_CLAIM));
        return feed;
    }

    @Transactional
    public Feed modify(FeedRequestDto dto) {
        Feed feed = feedRepository.findOne(dto.getFeedId());
        feed.setFeedContent(dto.getFeedContent());
        return feed;
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

    private static PageRequest getPageRequest() {
        return PageUtil.sortPageable(new Sort(DESC, "feedId"));
    }
}
