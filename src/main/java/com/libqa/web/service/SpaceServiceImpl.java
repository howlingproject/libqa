package com.libqa.web.service;

import com.google.common.collect.Iterables;
import com.libqa.application.enums.FavoriteTypeEnum;
import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.Space;
import com.libqa.web.domain.UserFavorite;
import com.libqa.web.repository.SpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yion on 2015. 3. 1..
 */
@Slf4j
@Service
public class SpaceServiceImpl implements SpaceService {

	@Autowired
	private SpaceRepository spaceRepository;

	@Autowired
	private KeywordService keywordService;

	@Autowired
	private UserFavoriteService userFavoriteService;

	@Override
	public Space save(Space space) {
		return spaceRepository.save(space);
	}

	@Override
	public List<Space> findAllByCondition(boolean isDeleted) {
		List<Space> spaceList =  spaceRepository.findAllByIsDeleted(PageUtil.sortId("DESC", "spaceId"), isDeleted);
		return spaceList;
	}

	@Override
	public Space findOne(Integer spaceId) {
		return spaceRepository.findOne(spaceId);
	}

	@Override
	public Space saveWithKeyword(Space space, Keyword keyword) {
		Space result = save(space);

		String[] keywordArrays = new String[0];
		String[] deleteKeywordArrays = new String[0];
		if(keyword.getKeywords() != null){
			keywordArrays = keyword.getKeywords().split(",");
		}
		if(keyword.getDeleteKeywords() != null){
			deleteKeywordArrays = keyword.getDeleteKeywords().split(",");
		}
		log.info(" keywordArrays : {}", keywordArrays.length);
		if (keywordArrays.length > 0) {
			keywordService.saveKeywordAndList(keywordArrays, deleteKeywordArrays, KeywordTypeEnum.SPACE, result.getSpaceId());
		}

		return result;
	}

	@Override
	public List<Space> findUserFavoriteSpace(Integer userId) {

		List<UserFavorite> userFavoriteList = new ArrayList<>();

		userFavoriteList = userFavoriteService.findByFavoriteTypeAndUserId(FavoriteTypeEnum.SPACE, userId);

		if (userFavoriteList.isEmpty()) {
			return null;
		}

		List<Space> mySpaceList = new ArrayList<>();
		for (UserFavorite favorite : userFavoriteList) {
			Space space = spaceRepository.findOne(favorite.getSpaceId());

			mySpaceList.add(space);
		}

		return mySpaceList;
	}

	@Override
	public Integer addSpaceFavorite(Integer spaceId, Integer userId, boolean isDeleted) {
		int result = 0;

		// 즐겨 찾기가 있는지 조회
		List<UserFavorite> userFavorites = userFavoriteService.findBySpaceIdAndUserId(spaceId, userId);

		UserFavorite userFavorite = Iterables.getFirst(userFavorites, null);
		try {
			if (userFavorite == null) {	// insert
				userFavorite = bindUserFavorite(spaceId, userId, isDeleted);
				userFavoriteService.save(userFavorite);
			} else {	// update
				updateFavorite(userFavorite, false);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			result = -1;
		}

		return result;
	}


	@Override
	public Integer cancelSpaceFavorite(Integer spaceId, Integer userId, boolean isDeleted) {
		int result = 0;

		// 즐겨 찾기가 있는지 조회
		List<UserFavorite> userFavorites = userFavoriteService.findBySpaceIdAndUserIdAndIsDeleted(spaceId, userId, false);

		UserFavorite userFavorite = Iterables.getFirst(userFavorites, null);
		try {
			if (userFavorite != null) {    // 즐겨 찾기가 이미 있을 경우 수정
				updateFavorite(userFavorite, true);
				return 1;
			} // 없는 경우에는 즐겨찾기 삭제를 할 수 없음
		} catch (Exception e) {
			e.printStackTrace();
			result = -1;
		}

		return result;
	}


	public UserFavorite bindUserFavorite(Integer spaceId, Integer userId, boolean isDeleted) {
		UserFavorite userFavorite;
		userFavorite = new UserFavorite();
		userFavorite.setFavoriteType(FavoriteTypeEnum.SPACE);
		userFavorite.setUserId(userId);
		userFavorite.setInsertDate(new Date());
		userFavorite.setSpaceId(spaceId);
		userFavorite.setDeleted(isDeleted);
		return userFavorite;
	}

	public void updateFavorite(UserFavorite userFavorite, boolean isDeleted) {
		userFavorite.setDeleted(isDeleted);
		userFavorite.setUpdateDate(new Date());
		userFavoriteService.save(userFavorite);
	}

}
