package com.libqa.web.service.qa;

import com.libqa.web.domain.QaRecommend;
import com.libqa.web.repository.QaRecommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yong on 2016-01-31.
 *
 * @author yong
 */
@Slf4j
@Service
public class QaRecommendServiceImpl implements QaRecommendService {

	@Autowired
	QaRecommendRepository qaRecommendRepository;

	@Override
	public List<QaRecommend> findByUserIdAndIsCommendTrue(Integer userId) {
		return qaRecommendRepository.findByUserIdAndIsCommendTrue(userId);
	}

	@Override
	public QaRecommend findByQaIdAndUserIdAndIsCommend(Integer qaId, Integer userId, boolean isCommend) {
		return qaRecommendRepository.findByQaIdAndUserIdAndIsCommend(qaId, userId, isCommend);
	}

	@Override
	public QaRecommend saveRecommendUp(QaRecommend paramQaRecommend, Integer userId) {
		return null;
	}

	@Override
	public QaRecommend saveRecommendDown(QaRecommend paramQaRecommend, Integer userId) {
		return null;
	}
}
