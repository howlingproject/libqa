package com.libqa.config.security;

import com.libqa.application.dto.SecurityUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : yion
 * @Date : 2015. 4. 19.
 * @Description :
 */
@Slf4j
public class PopulateGlobalAttrInterceptor implements HandlerInterceptor {

    private Map<String, Object> properties = new HashMap<>();

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mav) throws Exception {
        if (mav == null) return;

        boolean isRedirectView = mav.getView() instanceof RedirectView;
        boolean isViewObject = mav.getView() != null;
        boolean viewNameStartsWithRedirect =
                (mav.getViewName() == null ? true : mav.getViewName().startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX));
        if (mav.hasView() && ((isViewObject && !isRedirectView) || (!isViewObject && !viewNameStartsWithRedirect))) {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                return;
            }

            String email = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();
            Collection<? extends GrantedAuthority> auths = authentication.getAuthorities();

            // 미 로그인 사용자일 경우 role : ROLE_ANONYMOUS
            List<GrantedAuthority> grantedAuths = (List<GrantedAuthority>) authentication.getAuthorities();
            System.out.println("### grantedAuthority = " + grantedAuths.get(0));

            log.info("# PopulateGlobalAttrInterceptor user info = {}", email);
            log.info("# PopulateGlobalAttrInterceptor authentication.isAuthenticated() = {}", authentication.isAuthenticated());
            log.info("# PopulateGlobalAttrInterceptor authentication.getDetails().toString() = {}", authentication.getDetails().toString());
            log.info("# PopulateGlobalAttrInterceptor authentication.getDetails().getName() = {}", authentication.getName());
            log.info("# PopulateGlobalAttrInterceptor authentication.gerRole() = {}", grantedAuths.get(0));
            SecurityUserDto securityUserDto = new SecurityUserDto();
            securityUserDto.setUserEmail(email);
            securityUserDto.setRole(grantedAuths.get(0).toString());
            securityUserDto.setUserIp(req.getRemoteAddr());
            securityUserDto.setAuthenticated(authentication.isAuthenticated());

            addCommonModelData(mav, securityUserDto);
        }
    }


    private void addCommonModelData(ModelAndView mav, SecurityUserDto securityUserDto) {
        properties.put("loginUserInfo", securityUserDto);

        mav.getModel().putAll(properties);
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res,
                                Object handler, Exception ex) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        return true;
    }

}