package com.libqa.web.service;

import com.libqa.application.enums.FavoriteTypeEnum;
import com.libqa.web.domain.UserFavorite;
import com.libqa.web.repository.UserFavoriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 5. 20.
 * @Description :
 */
@Slf4j
@Service
public class UserFavoriteServiceImpl implements UserFavoriteService {


    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @Override
    public List<UserFavorite> findByFavoriteTypeAndUserId(FavoriteTypeEnum favoriteTypeEnum, Integer userId) {
        return userFavoriteRepository.findByFavoriteTypeAndUserId(favoriteTypeEnum, userId);
    }

    @Override
    public List<UserFavorite> findBySpaceIdAndUserIdAndIsDeleted(Integer spaceId, Integer userId, boolean isDeleted) {
        return userFavoriteRepository.findBySpaceIdAndUserIdAndIsDeleted(spaceId, userId, isDeleted);
    }

    @Override
    public void save(UserFavorite userFavorite) {
        userFavoriteRepository.save(userFavorite);
    }

}
