package com.libqa.config;

import com.libqa.application.util.RequestUtil;
import com.libqa.application.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 8. 30.
 * @Description :
 */
@Slf4j
public class LoginUserHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestUtil.printRequest(request, "LoginUserHandlerInterceptor ");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String login = "0";
        String userEmail = "";
        String role = "";

        log.info("#### interceptor authentication : {} ", authentication);
        if (authentication == null) {
            login = "0";
        } else {
            // modelAndView null 체크 해야함
            userEmail = authentication.getName();

            if (!isInvalidUser(userEmail)) {
                List<GrantedAuthority> grantedAuths = (List<GrantedAuthority>) authentication.getAuthorities();
                log.info("### grantedAuthority = {}", grantedAuths.get(0));

                log.info("@Interceptor authentication.isAuthenticated() = {}", authentication.isAuthenticated());
                log.info("@Interceptor authentication.getDetails().toString() = {}", authentication.getDetails().toString());
                log.info("@Interceptor authentication.getDetails().getName() = {}", authentication.getName());
                log.info("@Interceptor authentication.gerRole() = {}", grantedAuths.get(0));
                login = "1";
                role = String.valueOf(grantedAuths.get(0));

            }
            log.info("### LoginUserInterceptor userEmail  = {}", userEmail);
            log.info("### LoginUserInterceptor isInvalidUser(userEmail)  = {}", isInvalidUser(userEmail));
        }

        request.setAttribute("isLogin", login);
        request.setAttribute("userEmail", userEmail);
        request.setAttribute("userRole", role);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("# postHandle  request.get isLogin = {}", request.getAttribute("isLogin"));
        log.info("# postHandle  request.get userEmail = {}", request.getAttribute("userEmail"));
        log.info("# postHandle  request.get userRole = {}", request.getAttribute("userRole"));

        String isLogin = (String) request.getAttribute("isLogin");


        if (StringUtil.nullToString(isLogin, "").equals("1")) {
            if (modelAndView == null) {
                log.info("#### modelandview 가 널임 ");
            } else {
                modelAndView.addObject("isLogin", request.getAttribute("isLogin"));
                modelAndView.addObject("userEmail", request.getAttribute("userEmail"));
                modelAndView.addObject("userRole", request.getAttribute("userRole"));
            }
        }

    }

    private boolean isInvalidUser(String userEmail) {
        return StringUtils.isBlank(userEmail) || "anonymousUser".equals(userEmail);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("#### interceptor afterCompletion ");
    }
}
