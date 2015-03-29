package com.libqa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @Author : yion
 * @Date : 2015. 3. 29.
 * @Description :
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/", "/resource/**").permitAll()
                .antMatchers("/user/**", "/space", "/space/**", "/feed/main", "/qa/main", "/wiki/main").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin().failureUrl("/user/login?error")
                .defaultSuccessUrl("/")
                .loginPage("/user/login")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
}
