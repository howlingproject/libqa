package com.libqa.web.repository;

import com.libqa.web.domain.FeedReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedReplyRepository extends JpaRepository<FeedReply, Long> {
    FeedReply findByFeedId(Long feedId);
}
