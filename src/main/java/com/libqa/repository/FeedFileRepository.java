package com.libqa.repository;

import com.libqa.domain.FeedFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface FeedFileRepository extends JpaRepository<FeedFile, Long> {
}
