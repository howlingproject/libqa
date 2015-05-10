package com.libqa.web.repository;

import com.libqa.web.domain.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findByIsDeleted(boolean isDeleted, Pageable pageable);
}
