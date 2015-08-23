package com.libqa.application.util;

import com.libqa.application.enums.RoleEnum;
import com.libqa.web.domain.User;
import com.libqa.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
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

        log.info("### userEmail  = {}", userEmail);
        log.info("### isInvalidUser(userEmail)  = {}", isInvalidUser(userEmail));

        if (isInvalidUser(userEmail)) {
            User user = new User();
            user.setRole(RoleEnum.GUEST);
            return user;
        } else {
            return userService.findByEmail(userEmail);
        }

    }

    private boolean isInvalidUser(String userEmail) {
        return StringUtils.isBlank(userEmail) || "anonymousUser".equals(userEmail);
    }
}
