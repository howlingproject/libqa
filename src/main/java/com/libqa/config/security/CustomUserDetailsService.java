package com.libqa.config.security;

import com.libqa.web.domain.User;
import com.libqa.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author : yion
 * @Date : 2015. 4. 18.
 * @Description :
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        User user = userService.findByEmail(userEmail);

        if (user == null) {
            throw new UsernameNotFoundException("사용자 이메일 (" + userEmail + ") 정보를 찾을 수 없습니다.");
        }

        SecurityUser securityUser = new SecurityUser(user);

        System.out.println("@@@ securityUser.getPassword() = " + securityUser.getPassword());
        System.out.println("@@@ securityUser.getUserPass() = " + securityUser.getUserPass());
        System.out.println("@@@ user.getUserPass() = " + user.getUserPass());

        return new SecurityUser(user);
    }
}
