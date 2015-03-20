package com.libqa.web.service;

import com.libqa.web.domain.QaContent;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface QaService {
    QaContent saveQaContentAndKeyword(QaContent qaContent);
}
