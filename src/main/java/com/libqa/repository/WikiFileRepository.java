package com.libqa.repository;

import com.libqa.domain.WikiFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface WikiFileRepository extends JpaRepository<WikiFile, Long> {
}
