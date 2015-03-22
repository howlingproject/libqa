package com.libqa.web.service;

import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.QaContent;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface QaService {
    QaContent saveQaContentAndKeyword(QaContent qaContent);

    QaContent findById(Integer qaId);
}
