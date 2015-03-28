package com.libqa.web.repository;

import com.libqa.web.domain.QaContent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 1..
 */
public interface QaContentRepository extends JpaRepository<QaContent, Integer> {
    QaContent findOneByQaIdAndIsDeleted(Integer qaId, boolean isDeleted);
}
