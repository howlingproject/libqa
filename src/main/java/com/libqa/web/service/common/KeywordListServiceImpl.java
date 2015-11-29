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
    public boolean saveKeywordList(Object obj, KeywordType keywordType) {
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
        log.info("### keywordType = {}", keywordType);
        List<KeywordList> keywordList = new ArrayList<>();
        KeywordType paramEnum = KeywordType.valueOf(keywordType);

        keywordList = keywordListRepository.findByKeywordTypeAndIsDeleted(paramEnum, isDeleted);

        return keywordList;
    }
}
