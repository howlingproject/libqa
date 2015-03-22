package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.QaContent;
import com.libqa.web.repository.QaContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
@Slf4j
@Service
public class QaServiceImpl implements QaService {

    @Autowired
    QaContentRepository qaRepository;

    @Autowired
    KeywordService keywordService;

    @Override
    public QaContent saveQaContentAndKeyword(QaContent qaContentInstance) {
        QaContent qaContent;
        try {
            qaContentInstance.setUserId(1);
            qaContentInstance.setUserNick("용퓌");
            qaContentInstance.setInsertUserId(1);
            qaContentInstance.setInsertDate(new Date());
            qaContentInstance.setUpdateDate(new Date());
            qaContent = qaRepository.save(qaContentInstance);

            String [] keywordArrays = qaContentInstance.getKeywords().split(",");
            if (keywordArrays.length > 0) {
                keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.QA, qaContent.getQaId());
            }
        }catch(Exception e){
            log.info(e.toString());
            throw new RuntimeException("제발제발!!!!!");
        }
        return qaContent;
    }

    @Override
    public QaContent findById(Integer qaId) {
        return qaRepository.findOne(qaId);
    }

}
