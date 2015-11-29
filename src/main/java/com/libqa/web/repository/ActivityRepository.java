package com.libqa.web.repository;

import com.libqa.web.domain.Activity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 6. 28.
 * @Description :
 */
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findBySpaceIdAndIsDeleted(Integer spaceId, boolean isDeleted, Sort sort);
    List<Activity> findByWikiId(Integer WikiId);
}

