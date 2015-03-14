package com.libqa.repository;

import com.libqa.domain.QaContent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 1..
 */
public interface QaContentRepository extends JpaRepository<QaContent, Integer> {
}
