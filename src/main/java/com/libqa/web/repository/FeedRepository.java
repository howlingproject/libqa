package com.libqa.web.repository;

import com.libqa.web.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface FeedRepository extends JpaRepository<Feed, Long> {
}
