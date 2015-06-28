package com.libqa.web.repository;

import com.libqa.web.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author : yion
 * @Date : 2015. 6. 28.
 * @Description :
 */
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
}

