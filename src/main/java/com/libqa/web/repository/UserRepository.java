package com.libqa.web.repository;

import com.libqa.application.enums.Role;
import com.libqa.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yion on 2015. 1. 25..
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserEmail(String userEmail);

    User findByUserNick(String userNick);

    List<User> findByRoleAndIsDeleted(Role role, boolean isDeleted);

    List<User> findAllByIsDeleted(boolean isDeleted);
}
