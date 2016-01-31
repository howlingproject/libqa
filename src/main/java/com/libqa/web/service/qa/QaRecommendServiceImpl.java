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
}
