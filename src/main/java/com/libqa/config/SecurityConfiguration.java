package com.libqa.config;


import com.libqa.application.handler.LoginHandler;
import com.libqa.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @Author : yion
 * @Date : 2015. 3. 29.
 * @Description :
 */
@Slf4j
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
                .ignoring()
                .antMatchers("/", "/resource/**");

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        log.info("## configure httpSecurity = {}", httpSecurity);

        httpSecurity.authorizeRequests()
                .antMatchers("/user/**", "/space", "/space/**", "/feed/main", "/qa", "/qa/**", "/wiki/**", "/common/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        log.info("### AuthenticationSuccessHandler");
        return new LoginHandler("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("### SecurityConfiguration configure = {} ", auth);


        auth.inMemoryAuthentication().withUser("userEmail").password("userPass").roles("USER");

    }
}
