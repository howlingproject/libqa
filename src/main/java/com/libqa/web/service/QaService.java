package com.libqa.web.service;

import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;

import java.util.List;
import java.util.Map;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface QaService {
    QaContent saveWithKeyword(QaContent qaContent, QaFile qaFiles)throws Exception;

    QaContent findByQaId(Integer qaId, boolean isDeleted);

    List<QaContent> findByIsReplyedAndDayType(Map<String, String> params);

    boolean deleteWithKeyword(Integer qaId);
}
