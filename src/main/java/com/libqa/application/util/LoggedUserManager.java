package com.libqa.application.util;

import com.libqa.web.domain.User;
import com.libqa.web.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggedUserManager {

    @Autowired
    private UserService userService;

    /**
     * 로그인 사용자 정보를 조회한다.
     *
     * @return logged user
     */
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        log.debug("### userEmail:{}, isGuestUser:{}", userEmail, isGuestUser(userEmail));

        if (isGuestUser(userEmail)) {
            return User.createGuest();
        }

        return userService.findByEmail(userEmail);
    }

    private boolean isGuestUser(String userEmail) {
        return StringUtils.isBlank(userEmail) || "anonymousUser".equals(userEmail);
    }

}
