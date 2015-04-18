package com.libqa.config.security;

import com.libqa.web.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author : yion
 * @Date : 2015. 4. 18.
 * @Description :
 */
public class SecurityUser extends User implements UserDetails {
    private static final long serialVersionUID = 1L;

    public SecurityUser(User user) {
        if (user != null) {
            this.setUserId(user.getUserId());
            this.setUserNick(user.getUserNick());
            this.setUserEmail(user.getUserEmail());
            this.setCertification(user.isCertification());
            this.setRole(user.getRole());
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (this.getRole() != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.getRole().name());
            authorities.add(authority);
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getUserPass();
    }

    @Override
    public String getUsername() {
        return super.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
