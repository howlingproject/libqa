package com.libqa.web.service;

import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface QaService {
    QaContent saveWithKeyword(QaContent qaContent, QaFile qaFile);

    QaContent findByQaId(Integer qaId, boolean isDeleted);
}
