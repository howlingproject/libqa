package com.libqa.web.service;

import com.libqa.web.domain.QaReply;
import com.libqa.web.repository.QaReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yong on 2015-04-12.
 *
 * @author yong
 */
@Slf4j
@Service
public class QaReplyServiceImpl implements QaReplyService {

    @Autowired
    QaReplyRepository qaReplyRepository;

    @Override
    public QaReply saveWithQaContent(QaReply qaReply) {
        return qaReplyRepository.save(qaReply);
    }
}
