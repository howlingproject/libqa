package com.libqa.web.service.qa;

import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaRecommend;
import com.libqa.web.repository.QaRecommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yong on 2016-01-31.
 *
 * @author yong
 */
@Slf4j
@Service
@Transactional
public class QaRecommendServiceImpl implements QaRecommendService {

    @Autowired
    QaService qaService;

    @Autowired
    QaRecommendRepository qaRecommendRepository;

    @Override
    public List<QaRecommend> findByUserIdAndIsCommendTrue(Integer userId) {
        return qaRecommendRepository.findByUserIdAndIsCommendTrue(userId);
    }

    @Override
    public QaRecommend findByQaIdAndUserIdAndIsCommend(Integer qaId, Integer userId, boolean isCommend) {
        return qaRecommendRepository.findByQaIdAndUserIdAndIsCommendAndIsCanceledFalse(qaId, userId, isCommend);
    }

    @Override
    public QaContent saveRecommend(QaRecommend paramQaRecommend, Integer userId, String userNick) throws Exception {
        try {
            QaRecommend preQaRecommend = qaRecommendRepository.findByQaIdAndUserIdAndIsCanceledFalse(paramQaRecommend.getQaId(), userId);
            int calculationCnt = 0;
            if (preQaRecommend != null) {
                calculationCnt = -1;
                preQaRecommend.setCanceled(true);
                qaService.saveRecommendCount(paramQaRecommend.getQaId(), preQaRecommend.getIsCommend(), calculationCnt);
            }
            paramQaRecommend.setUserId(userId);
            paramQaRecommend.setUserNick(userNick);
            QaRecommend newQaRecommend = qaRecommendRepository.save(paramQaRecommend);

            int newCalculationCnt = 1;
            return qaService.saveRecommendCount(paramQaRecommend.getQaId(), paramQaRecommend.getIsCommend(), newCalculationCnt);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    @Override
    public List<QaRecommend> findByQaIdAndIsCommend(Integer qaId, boolean isCommend) {
        return qaRecommendRepository.findByQaIdAndIsCommendAndIsCanceledFalse(qaId, isCommend);
    }

    @Override
    public int getCountByQaId(Integer qaId) {
        return qaRecommendRepository.countByQaIdAndIsCanceledFalse(qaId);
    }

}
