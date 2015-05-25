package com.libqa.web.repository;

import com.libqa.web.domain.FeedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedFileRepository extends JpaRepository<FeedFile, Long> {
    List<FeedFile> findByFeedFeedId(Long feedId);
}
