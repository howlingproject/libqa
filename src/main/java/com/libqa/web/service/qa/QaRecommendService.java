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
}
