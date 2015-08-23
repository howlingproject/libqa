package com.libqa.web.service;

import com.libqa.web.domain.Space;

import java.util.List;

/**
 * Created by yion on 2015. 3. 1..
 */
public interface SpaceService {

	Space save(Space space);

	/**
	 * 삭제되지 않은 공간 목록 조회
	 * @param isDeleted
	 * @return
	 */
	List<Space> findAllByCondition(boolean isDeleted);

	Space findOne(Integer spaceId);

	Space saveWithKeyword(Space space);

	/**
	 * 사용자가 추가한 즐겨찾기 공간 목록 조회
	 * @param userId
	 * @return
	 */
	List<Space> findUserFavoriteSpace(Integer userId);

	/**
	 * 즐겨 찾기 추가 (삭제)
	 * @param spaceId
	 * @param userId
	 * @param isDeleted
	 * @return
	 */
	Integer addSpaceFavorite(Integer spaceId, Integer userId, boolean isDeleted);

	Integer cancelSpaceFavorite(Integer spaceId, Integer userId, boolean isDeleted);
}
