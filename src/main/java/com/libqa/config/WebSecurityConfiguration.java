package com.libqa.config;

import com.libqa.application.handler.CustomLoginFailureHandler;
import com.libqa.application.handler.CustomLoginSuccessHandler;
import com.libqa.config.security.CustomAuthenticationProvider;
import com.libqa.config.security.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.sql.DataSource;

/**
 * @Author : yion
 * @Date : 2015. 3. 29.
 * @Description :
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final static String REMEMBER_ME_KEY = "LIBQA_REMEBMER_ME_KEY";

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/", "/resource/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/index", "/user/**", "/space", "/space/**", "/feed/**", "/qa", "/qa/**", "/qa/save", "/wiki/**", "/common/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/loginPage").usernameParameter("userEmail").passwordParameter("userPass")
                .loginProcessingUrl("/user/login/authenticate").permitAll()
                .successHandler(loginSuccessHandler())
                .failureHandler(new CustomLoginFailureHandler())
                .failureUrl("/login?error")
                .and()
                .rememberMe().key(REMEMBER_ME_KEY).rememberMeServices(tokenBasedRememberMeServices())
                .and()
                .logout().deleteCookies("remember-me").logoutUrl("/logoutUser").logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/access?error");

        //.logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RememberMeServices tokenBasedRememberMeServices() {
        TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsServiceImpl);
        tokenBasedRememberMeServices.setAlwaysRemember(true);
        tokenBasedRememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 31);
        tokenBasedRememberMeServices.setCookieName("libQaCookie");
        return tokenBasedRememberMeServices;
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        log.debug("#### login Success handler #####");
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }

}
