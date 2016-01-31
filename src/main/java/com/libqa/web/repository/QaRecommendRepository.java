package com.libqa.web.repository;

import com.libqa.web.domain.QaRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface QaRecommendRepository extends JpaRepository<QaRecommend, Integer> {
	List<QaRecommend> findByUserIdAndIsCommendTrue(Integer userId);
}
