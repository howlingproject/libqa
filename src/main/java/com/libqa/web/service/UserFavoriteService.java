package com.libqa.web.service;

import com.libqa.application.enums.FavoriteType;
import com.libqa.web.domain.UserFavorite;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 5. 20.
 * @Description : 사용자 즐겨찾기 서비스
 */
public interface UserFavoriteService {
    /**
     * 사용자가 즐겨찾기 한 타입 리스트 조회
     * @param typeEnum
     * @param userId
     * @return
     */
    List<UserFavorite> findByFavoriteTypeAndUserIdAndIsDeleted(FavoriteType typeEnum, Integer userId, boolean isDeleted);


    /**
     * 사용자가 추가한 공간 즐겨 찾기 조회
     * @param spaceId
     * @param userId
     * @return
     */
    List<UserFavorite> findBySpaceIdAndUserId(Integer spaceId, Integer userId);

    List<UserFavorite> findBySpaceIdAndUserIdAndIsDeleted(Integer spaceId, Integer userId, boolean isDeleted);

    void save(UserFavorite userFavorite);

}
