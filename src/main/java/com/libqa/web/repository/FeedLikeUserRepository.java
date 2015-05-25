package com.libqa.web.repository;

import com.libqa.web.domain.FeedLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeUserRepository extends JpaRepository<FeedLikeUser, Long> {
//    List<FeedLikeUser> findByFeedIdAndUserIdAndFeedLikeType(long feedId, Integer userId, FeedLikeTypeEnum feedLikeType);
}
