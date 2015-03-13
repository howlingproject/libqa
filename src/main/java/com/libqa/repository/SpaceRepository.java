package com.libqa.repository;

import com.libqa.application.enums.SpaceViewEnum;
import com.libqa.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface SpaceRepository extends JpaRepository<Space, Long> {
    void findByUserId(Integer userId);

    List<Space> findByLayout(SpaceViewEnum left);
}
