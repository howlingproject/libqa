package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.KeywordList;
import com.libqa.web.repository.KeywordListRepository;
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
public class KeywordListServiceImpl implements KeywordListService {

    @Autowired
    KeywordListRepository keywordListRepository;

    @Override
    public boolean saveKeywordList(Object obj, KeywordTypeEnum keywordType) {
        boolean successType = true;

//        Keyword keywordInstance = modelMapper.map(obj, Keyword.class);
//        try {
//            for (keyword in obj) {
//                def keywordName = keyword.keywordName
//                // TODO List KeywordList table에 아래의 조건으로 index를 걸어야함
//                def keywordListObj = findKeywordListByKeywordNameAndKeywordType(keywordName, keywordType)
//                if (keywordListObj == null) {
//                    keywordListObj = new KeywordList()
//                    keywordListObj.setKeywordName(keywordName)
//                    keywordListObj.setKeywordType(keywordType)
//                } else {
//                    keywordListObj.keywordCount = keywordListObj.getKeywordCount() + 1
//                }
//                save(keywordListObj)
//            }
//        }catch(e){
//            log.debug(e.printStackTrace())
//            successType = 0
//        }
        return successType;
    }

    @Override
    public List<KeywordList> findByKeywordType(String keywordType, boolean isDeleted) {
        List<KeywordList> keywordList = new ArrayList<>();
        switch (keywordType){
            case "WIKI" :
                keywordList = keywordListRepository.findByKeywordTypeAndIsDeleted(KeywordTypeEnum.WIKI, isDeleted);
            case "SPACE" :
                keywordList = keywordListRepository.findByKeywordTypeAndIsDeleted(KeywordTypeEnum.SPACE, isDeleted);
            case "QA" :
                keywordList = keywordListRepository.findByKeywordTypeAndIsDeleted(KeywordTypeEnum.QA, isDeleted);
        }
        return keywordList;
    }
}
