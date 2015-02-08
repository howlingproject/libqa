package com.libqa.repository;

import com.libqa.domain.FeedLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface FeedLikeUserRepository extends JpaRepository<FeedLikeUser, Long> {
}
