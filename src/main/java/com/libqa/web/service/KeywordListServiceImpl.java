package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
@Service
public class KeywordListServiceImpl implements KeywordListService {

    @Autowired
    ModelMapper modelMapper;

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
}
