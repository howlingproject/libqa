package com.libqa.web.repository;

import com.libqa.application.enums.FeedLikeTypeEnum;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedLikeUserRepository extends JpaRepository<FeedLikeUser, Long> {
    List<FeedLikeUser> findByFeedAndUserIdAndFeedLikeType(Feed feed, Integer userId, FeedLikeTypeEnum feedLikeTypeEnum);
}
