package com.libqa.web.repository;

import com.libqa.web.domain.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Integer> {
    List<Feed> findByIsDeletedFalse(Pageable pageable);
    List<Feed> findByFeedIdLessThanAndIsDeletedFalse(Integer lastFeedId, Pageable pageable);
    List<Feed> findByUserIdAndIsDeletedFalse(Integer userId, Pageable pageable);
    List<Feed> findByUserIdAndFeedIdLessThanAndIsDeletedFalse(Integer userId, Integer lastFeedId, Pageable pageable);
    Integer countFeedRepliesByFeedId(Integer feedId);
}
