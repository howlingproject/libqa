package com.libqa.web.service;

import com.libqa.application.enums.FavoriteTypeEnum;
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
    List<UserFavorite> findByFavoriteTypeAndUserId(FavoriteTypeEnum typeEnum, Integer userId);
}
