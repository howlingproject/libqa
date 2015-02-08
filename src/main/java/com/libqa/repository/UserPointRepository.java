package com.libqa.repository;

import com.libqa.domain.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
}
