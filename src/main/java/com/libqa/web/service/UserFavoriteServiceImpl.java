package com.libqa.web.service;

import com.libqa.application.enums.FavoriteType;
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
    public List<UserFavorite> findByFavoriteTypeAndUserId(FavoriteType favoriteType, Integer userId) {
        return userFavoriteRepository.findByFavoriteTypeAndUserId(favoriteType, userId);
    }

    @Override
    public List<UserFavorite> findBySpaceIdAndUserId(Integer spaceId, Integer userId) {
        return userFavoriteRepository.findBySpaceIdAndUserId(spaceId, userId);
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
