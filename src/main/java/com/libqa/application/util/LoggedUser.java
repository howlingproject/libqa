package com.libqa.application.util;

import com.libqa.web.domain.User;
import com.libqa.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedUser {

    @Autowired
    private UserService userService;

    /**
     * 로그인 사용자 정보를 조회한다.
     * @return logged user
     */
    public User get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (isInvalidUser(userEmail)) {
            throw new RuntimeException(userEmail + " is invalid logged user!");
        }
        return userService.findByEmail(userEmail);
    }

    private boolean isInvalidUser(String userEmail) {
        return StringUtils.isBlank(userEmail) || "anonymousUser".equals(userEmail);
    }
}
