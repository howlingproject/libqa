package com.libqa.web.service.common;

import com.libqa.application.enums.KeywordType;
import com.libqa.web.domain.KeywordList;
import com.libqa.web.repository.KeywordListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
@Service
@Slf4j
public class KeywordListServiceImpl implements KeywordListService {

    @Autowired
    KeywordListRepository keywordListRepository;


    @Override
    public List<KeywordList> findByKeywordType(String keywordType, boolean isDeleted) {
        List<KeywordList> keywordList = new ArrayList<>();
        KeywordType paramEnum = KeywordType.valueOf(keywordType);

        keywordList = keywordListRepository.findByKeywordTypeAndIsDeleted(paramEnum, isDeleted);

        return keywordList;
    }
}
