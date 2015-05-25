package com.libqa.web.repository;

import com.libqa.web.domain.FeedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedFileRepository extends JpaRepository<FeedFile, Long> {
    FeedFile findByFeedId(Long feedId);
}
