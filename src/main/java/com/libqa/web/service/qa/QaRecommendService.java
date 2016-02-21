package com.libqa.web.service.qa;

import com.libqa.web.domain.QaRecommend;

import java.util.List;

/**
 * Created by yong on 2016-01-31.
 *
 * @author yong
 */
public interface QaRecommendService {
	List<QaRecommend> findByUserIdAndIsCommendTrue(Integer userId);

	QaRecommend findByQaIdAndUserIdAndIsCommend(Integer qaId, Integer userId, boolean isCommend);

	QaRecommend saveRecommendUp(QaRecommend paramQaRecommend, Integer userId);

	QaRecommend saveRecommendDown(QaRecommend paramQaRecommend, Integer userId);
}
