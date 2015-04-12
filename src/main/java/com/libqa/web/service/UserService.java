package com.libqa.web.service;

import com.libqa.application.Exception.UserNotCreateException;
import com.libqa.web.domain.User;

/**
 * @Author : yion
 * @Date : 2015. 4. 12.
 * @Description :
 */
public interface UserService {
    User createUser(String userEmail, String userNick, String password, String loginType) throws UserNotCreateException;
}
