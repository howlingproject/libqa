package com.libqa.web.repository;

import com.libqa.application.enums.FavoriteType;
import com.libqa.web.domain.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Integer>{

    List<UserFavorite> findBySpaceIdAndUserId(Integer spaceId, Integer userId);

    List<UserFavorite> findByFavoriteTypeAndUserIdAndIsDeleted(FavoriteType favoriteType, Integer userId, boolean isDeleted);

    List<UserFavorite> findBySpaceIdAndUserIdAndIsDeleted(Integer spaceId, Integer userId, boolean isDeleted);

}
