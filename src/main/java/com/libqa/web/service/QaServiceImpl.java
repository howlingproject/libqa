package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.domain.QaContent;
import com.libqa.repository.QaContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

    @Override
    public QaContent saveQaContentAndKeyword(QaContent qaContentInstance) {
//        QaContent qaContentInstance = modelMapper.map(qaCreateForm, QaContent.class);
        try {
            saveQa(qaContentInstance);
            keywordService.saveKeywordListAndKeyword(qaContentInstance, KeywordTypeEnum.QA);
        }catch(Exception e){
            log.info(e.toString());
            throw new RuntimeException("제발제발!!!!!");
        }
        return qaContentInstance;
    }

    private void saveQa(QaContent qaContentInstance) {
        qaContentInstance.setInsertDate(new Date());
        qaContentInstance.setUpdateDate(new Date());
        qaRepository.save(qaContentInstance);
    }

}
