package com.libqa.web.service.qa;

import com.libqa.application.dto.QaDto;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;
import com.libqa.web.domain.User;

import java.util.List;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface QaService {
    QaContent saveWithKeyword(QaContent requestQaContent, QaFile requestQaFiles, Keyword requestKeywords, User user)throws Exception;

    QaContent findByQaId(Integer qaId, boolean isDeleted);

    List<QaContent> findByIsReplyedAndDayType(QaDto qaDto);

    boolean deleteWithKeyword(Integer qaId);

    void saveIsReplyed(Integer qaId, User user, boolean isReplyed);

    QaContent updateWithKeyword(QaContent originQaContent, QaContent requestQaContent, QaFile requestQaFiles, Keyword requestKeywords, User user);

    List<QaContent> findByUserId(Integer userId);

    List<QaContent> findByQaIdIn(List<Integer> qaIds);

    QaContent view(Integer qaId);

    List<QaContent> searchRecentlyQaContentsByPageSize(Integer pageSize);

    Integer getQaTotalCount();

    Integer getQaNotReplyedCount();

    List<QaContent> getBestQaContents();

	QaContent saveRecommendCount(Integer qaId, boolean commend, int calculationCnt);
}
