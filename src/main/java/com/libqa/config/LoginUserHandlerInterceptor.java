package com.libqa.config;

import com.libqa.application.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 8. 30.
 * @Description :
 */
@Slf4j
public class LoginUserHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        RequestUtil.printRequest(request, "LoginUserHandlerInterceptor ");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isLogin = false;
        String userRole = "";
        String userEmail = "";

        log.debug("#### interceptor authentication : {} ", authentication);
        if (authentication != null) {
            // modelAndView null 체크 해야함
            userEmail = authentication.getName();

            if (!isInvalidUser(userEmail)) {
                List<GrantedAuthority> grantedAuths = (List<GrantedAuthority>) authentication.getAuthorities();
                log.debug("### grantedAuthority = {}", grantedAuths.get(0));

                log.debug("@Interceptor authentication.isAuthenticated() = {}", authentication.isAuthenticated());
                log.debug("@Interceptor authentication.getDetails().toString() = {}", authentication.getDetails().toString());
                log.debug("@Interceptor authentication.getDetails().getName() = {}", authentication.getName());
                log.debug("@Interceptor authentication.gerRole() = {}", grantedAuths.get(0));

                isLogin = true;
                userRole = String.valueOf(grantedAuths.get(0));

            }
            log.debug("### LoginUserInterceptor userEmail  = {}", userEmail);
            log.debug("### LoginUserInterceptor isInvalidUser(userEmail)  = {}", isInvalidUser(userEmail));
        }

        request.setAttribute("isLogin", isLogin);
        request.setAttribute("userEmail", userEmail);
        request.setAttribute("userRole", userRole);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("# postHandle  request.get isLogin = {}", request.getAttribute("isLogin"));
        log.debug("# postHandle  request.get userEmail = {}", request.getAttribute("userEmail"));
        log.debug("# postHandle  request.get userRole = {}", request.getAttribute("userRole"));

        Boolean isLogin = (Boolean) request.getAttribute("isLogin");
        String userEmail = (String) request.getAttribute("userEmail");
        String userRole = (String) request.getAttribute("userRole");

        if(modelAndView != null) {
            modelAndView.addObject("userEmail", userEmail);
            modelAndView.addObject("userRole", userRole);
            modelAndView.addObject("isLogin", isLogin);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("#### interceptor afterCompletion ");
    }

    private boolean isInvalidUser(String userEmail) {
        return StringUtils.isBlank(userEmail) || "anonymousUser".equals(userEmail);
    }
}
