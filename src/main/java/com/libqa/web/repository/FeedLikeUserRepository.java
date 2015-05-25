package com.libqa.web.repository;

import com.libqa.application.enums.FeedLikeTypeEnum;
import com.libqa.web.domain.FeedLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface FeedLikeUserRepository extends JpaRepository<FeedLikeUser, Long> {
    List<FeedLikeUser> findByFeedIdAndUserIdAndFeedLikeType(long feedId, Integer userId, FeedLikeTypeEnum feedLikeType);
}
