package com.libqa.repository;

import com.libqa.domain.SpaceAccessUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface SpaceAccessUserRepository extends JpaRepository<SpaceAccessUser, Long> {
}
