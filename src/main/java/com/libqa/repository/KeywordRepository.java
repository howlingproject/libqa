package com.libqa.repository;

import com.libqa.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
