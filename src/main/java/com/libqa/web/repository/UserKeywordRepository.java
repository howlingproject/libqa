package com.libqa.web.repository;

import com.libqa.web.domain.User;
import com.libqa.web.domain.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface UserKeywordRepository extends JpaRepository<UserKeyword, Integer> {
    List<UserKeyword> findByUserAndIsDeleted(User user, boolean isDeleted);
}
