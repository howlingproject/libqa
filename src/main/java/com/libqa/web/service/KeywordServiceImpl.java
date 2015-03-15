package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.domain.Keyword;
import com.libqa.domain.KeywordList;
import com.libqa.repository.KeywordListRepository;
import com.libqa.repository.KeywordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
@Service
@Slf4j
public class KeywordServiceImpl implements KeywordService {

	@Autowired
	KeywordRepository keywordRepository;

	@Autowired
	KeywordListRepository keywordListRepository;

	/**
	 * 키워드를 저장한 후 통계 데이터를 생성한다.
	 * 키워드 리스트는 객체 (Space, Wiki, QaContent의 List 타입으로 넘어온다)
	 * @param keywordParams
	 * @param keywordType
	 * @return
	 */
	@Override
	public boolean saveKeywordAndList(String[] keywordParams, KeywordTypeEnum keywordType, Integer entityId) {
		Assert.notNull(keywordParams, "키워드가 존재하지 않습니다.");
		Assert.notNull(keywordType, "키워드 타입이 존재하지 않습니다.");
		boolean result = false;

		log.info("#keywordType : {}", keywordType);
		log.info("#keywordType : {}", keywordType.ordinal());
		log.info("#keywordType : {}", keywordType.name());
		try {
			for (String param : keywordParams) {
				saveKeyword(param, keywordType, entityId);
				saveKeywordList(param, keywordType);
			}
			result = true;
		} catch (Exception e) {
			log.error("키워드 저장시 에러가 발생했습니다.", e.getMessage());
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
		log.info("# keywordType : {}", keywordType);
		log.info("# KeywordTypeEnum.SPACE : {}", KeywordTypeEnum.SPACE);
		log.info(String.valueOf(keywordType.equals(KeywordTypeEnum.SPACE)));
		Keyword keyword = new Keyword();

		log.info("## 1");
		if (keywordType.equals(KeywordTypeEnum.SPACE)) {
			log.info("## 2");
			keyword.setSpaceId(entityId);
		} else if (keywordType.equals(KeywordTypeEnum.WIKI)) {
			log.info("## 3");
			keyword.setWikiId(entityId);
		} else {
			log.info("## 4");
			keyword.setQaId(entityId);
		}

		keyword.setKeywordName(param);
		log.info("## 5");
		keyword.setKeywordType(keywordType);
		log.info("## 6");
		keyword.setInsertDate(new Date());
		log.info("## 7 : {}", keyword);
		try {
			keywordRepository.saveAndFlush(keyword);
		} catch (Exception e) {
			log.error("값 세팅 중 에러발생 ");
			e.printStackTrace();

		}

	}

	/**
	 * 키워드 리스트 통계 데이터를 생성한다. 없을 경우 insert, 존재할 경우 count + 1 update 를 수행한다.
	 * @param param
	 * @param keywordType
	 */

	@Transactional
	public void saveKeywordList(String param, KeywordTypeEnum keywordType) {
		KeywordList keywordList = keywordListRepository.findByKeywordNameAndKeywordType(param, keywordType);
		log.info("### keywordList : {}", keywordList);
		if (keywordList != null) {
			log.info("## 값이 존재");
			keywordList.setKeywordCount(keywordList.getKeywordCount() + 1);
		} else {
			log.info("## 값을 생성 ");
			keywordList = new KeywordList(param, keywordType, new Integer(1));
		}

		try {
			keywordListRepository.saveAndFlush(keywordList);
		} catch (Exception e) {
			log.info("## 에러에러  ");
			e.printStackTrace();
		}

	}

}
