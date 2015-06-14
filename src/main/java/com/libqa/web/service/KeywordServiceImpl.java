package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.application.util.StringUtil;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.KeywordList;
import com.libqa.web.repository.KeywordListRepository;
import com.libqa.web.repository.KeywordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
@Slf4j
@Service
public class KeywordServiceImpl implements KeywordService {

	@Autowired
	KeywordRepository keywordRepository;

	@Autowired
	KeywordListRepository keywordListRepository;

	@Override
	public List<Keyword> findByQaId(Integer qaId, boolean isDeleted) {
		return keywordRepository.findAllByQaIdAndIsDeleted(qaId, isDeleted);
	}

	@Override
	public List<Keyword> findAllByKeywordTypeAndKeywordNameAndIsDeleted(KeywordTypeEnum keywordType, String keywordName, boolean isDeleted) {
		if("".equals(keywordName) || null == keywordName){
			return keywordRepository.findAllByKeywordTypeAndIsDeleted(keywordType, isDeleted);
		} else {
			return keywordRepository.findAllByKeywordTypeAndKeywordNameAndIsDeleted(keywordType, keywordName, isDeleted);
		}
	}

    @Override
    public List<Keyword> findByWikiId(Integer wikiId, boolean isDeleted) {
        return keywordRepository.findAllByWikiIdAndIsDeleted(wikiId, isDeleted);
    }

    @Override
    public List<Keyword> findBykeywordId(Integer keywordId, boolean isDeleted) {
        return keywordRepository.findAllByKeywordIdAndIsDeleted(keywordId, isDeleted);
    }

    /**
	 * 키워드를 저장한 후 통계 데이터를 생성한다.
	 * 키워드 리스트는 객체 (Space, Wiki, QaContent)의 List 타입으로 넘어온다)
	 * @param keywordParams
	 * @param keywordType
	 * @return
	 */
	@Override
	@Transactional
	public boolean saveKeywordAndList(String[] keywordParams, KeywordTypeEnum keywordType, Integer entityId) {
		Assert.notNull(keywordParams, "키워드가 존재하지 않습니다.");
		Assert.notNull(keywordType, "키워드 타입이 존재하지 않습니다.");
		Assert.notNull(entityId, "키 값이 존재하지 않습니다.");
		boolean result = false;

		try {
			for (int i = 0; i < keywordParams.length; i++) {
				log.info("@ param : {}", keywordParams[i]);
				saveKeyword(keywordParams[i], keywordType, entityId);
				saveKeywordList(keywordParams[i], keywordType);
			}
			result = true;
		} catch (Exception e) {
			log.error("키워드 저장시 에러가 발생했습니다.", e);
			result = false;
		}
		return result;
	}


	/**
	 * 키워드를 저장한다.
	 * @param param
	 * @param keywordType
	 * @param entityId
	 */
	@Transactional
	public void saveKeyword(String param, KeywordTypeEnum keywordType, Integer entityId) {
		Assert.notNull(StringUtil.defaultString(param, null), "키워드 값이 존재하지 않습니다.");
		Keyword keyword = new Keyword();

		if (keywordType.equals(KeywordTypeEnum.SPACE)) {
			keyword.setSpaceId(entityId);
		} else if (keywordType.equals(KeywordTypeEnum.WIKI)) {
			keyword.setWikiId(entityId);
		} else {
			keyword.setQaId(entityId);
		}

		keyword.setKeywordName(param);
		keyword.setKeywordType(keywordType);
		keyword.setInsertDate(new Date());
		keywordRepository.saveAndFlush(keyword);
	}

	/**
	 * 키워드 리스트 통계 데이터를 생성한다. 없을 경우 insert, 존재할 경우 count + 1 update 를 수행한다.
	 * @param param
	 * @param keywordType
	 */

	@Transactional
	public void saveKeywordList(String param, KeywordTypeEnum keywordType) {
		Assert.notNull(StringUtil.defaultString(param, null), "키워드 값이 존재하지 않습니다.");
		List<KeywordList> keywordList = keywordListRepository.findByKeywordNameAndKeywordType(param, keywordType);

		if (keywordList.isEmpty()) {
			KeywordList keyword = new KeywordList(param, keywordType, new Integer(1));
			// insert
			keywordListRepository.save(keyword);
		} else {
			for (KeywordList keys : keywordList) {
				int count = keys.getKeywordCount() + 1;
				keys.setKeywordCount(count);
				// update
				keywordListRepository.save(keywordList);
			}
		}
	}

}
