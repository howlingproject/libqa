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
    QaContent saveWithKeyword(QaContent qaContent, List<QaFile> qaFiles);

    QaContent findByQaId(Integer qaId, boolean isDeleted);

    List<QaContent> findByIsReplyedAndDaytype(Map<String, String> params);
}
