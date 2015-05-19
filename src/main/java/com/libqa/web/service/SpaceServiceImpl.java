package com.libqa.web.service;

import com.libqa.application.enums.FavoriteTypeEnum;
import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.application.enums.SpaceViewEnum;
import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.Space;
import com.libqa.web.domain.UserFavorite;
import com.libqa.web.repository.SpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	public Space saveWithKeyword(Space space) {
		Space result = save(space);

		String[] keywordArrays = space.getKeywords().split(",");
		log.info(" keywordArrays : {}", keywordArrays.length);
		if (keywordArrays.length > 0) {
			keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.SPACE, result.getSpaceId());
		}

		return result;
	}

	@Override
	public List<Space> findUserFavoriteSpace(Integer userId) {
		if (userId == null || userId == 0) {
			return null;
		}

		List<UserFavorite> userFavoriteList = userFavoriteService.findByFavoriteTypeAndUserId(FavoriteTypeEnum.SPACE, userId);

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


}
