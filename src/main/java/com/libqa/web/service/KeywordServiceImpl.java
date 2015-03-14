package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    KeywordListService keywordListService;

    @Override
    public boolean saveKeywordListAndKeyword(Object obj, KeywordTypeEnum keywordType) {
        boolean successType = true;
//        Keyword keywordInstance = modelMapper.map(obj, Keyword.class);
        try {
            successType = keywordListService.saveKeywordList(obj, keywordType);
            if(successType) {
//                for (Keyword keywordObj : obj) {
//                for (Keyword keywordObj : keywordInstance.getKeywords()) {
//                    if (keywordObj instanceof Space) {
//                        keyword.setSpaceId(obj.spaceId)
//                    } else if (obj instanceof QaContent) {
//                        keyword.setQaId(obj.qaId)
//                    } else if (obj instanceof Wiki) {
//                        keyword.setWikiId(obj.wikiId)
//                    }
//                    keywordObj.setKeywordType(keywordType);
//                    keywordObj.setInsertDate(new Date());
//                    keywordObj.setUpdateDate(new Date());
//                    keywordRepository.save(keywordObj);
//                }
            }
        }catch(Exception e){
            successType = false;
        }
        return successType;
    }
}
