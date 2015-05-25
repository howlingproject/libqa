package com.libqa.web.service;

import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedLikeUser;
import com.libqa.web.repository.FeedLikeUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.libqa.application.enums.FeedLikeTypeEnum.FEED;

@Slf4j
@Service
public class FeedLikeUserService {
    @Autowired
    private FeedLikeUserRepository feedLikeUserRepository;

    public boolean isLikable(Feed feed) {
//        List<FeedLikeUser> feedLikeUsers = feedLikeUserRepository.findByFeedIdAndUserIdAndFeedLikeType(feed.getFeedId(), feed.getUserId(), FEED);
        
//        log.debug("feedLikeUsers : {}", feedLikeUsers);
//        return CollectionUtils.isEmpty(feedLikeUsers);
        return true;
    }

    public void saveByFeed(Feed feed, boolean isCanceled) {
        FeedLikeUser feedLikeUser = new FeedLikeUser();
        feedLikeUser.setFeedLikeType(FEED);
        feedLikeUser.setReplyId(-1l);
        feedLikeUser.setCanceled(isCanceled);
        feedLikeUser.setInsertDate(new Date());
        feedLikeUser.setUpdateDate(new Date());
        feedLikeUser.setUserId(feed.getUserId());
        feedLikeUser.setUserNick(feed.getUserNick());
        feedLikeUser.setInsertUserId(feed.getUserId());
        feedLikeUser.setUpdateUserId(feed.getUserId());
        feedLikeUser.setFeed(feed);
        feedLikeUserRepository.save(feedLikeUser);
    }
}
