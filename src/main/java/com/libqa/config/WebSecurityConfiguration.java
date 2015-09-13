package com.libqa.config;


import com.libqa.application.handler.LoginHandler;
import com.libqa.config.security.CustomAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @Author : yion
 * @Date : 2015. 3. 29.
 * @Description :
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableWebMvcSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

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

        httpSecurity
                .authorizeRequests()
                .antMatchers("/user/**", "/space", "/space/**", "/feed/**", "/qa", "/qa/**", "/qa/save", "/wiki/**", "/common/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/loginPage").usernameParameter("userEmail").passwordParameter("userPass")
                .successHandler(loginSuccessHandler())
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logoutUser")).logoutSuccessUrl("/index")
                .and()
                .exceptionHandling().accessDeniedPage("/access?error")
                .and()
                .authenticationProvider(customAuthenticationProvider)

        ;

        //.logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))

    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        log.info("#### login Success handler #####");
        return new LoginHandler();
    }
}
