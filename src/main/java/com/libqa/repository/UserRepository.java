package com.libqa.repository;

import com.libqa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yion on 2015. 1. 25..
 */
public interface UserRepository extends JpaRepository<User, Long> {
}