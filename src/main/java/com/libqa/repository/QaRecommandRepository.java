package com.libqa.repository;

import com.libqa.domain.QaRecommand;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface QaRecommandRepository extends JpaRepository<QaRecommand, Long> {
}
