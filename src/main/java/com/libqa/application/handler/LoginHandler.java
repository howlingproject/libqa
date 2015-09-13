package com.libqa.application.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libqa.application.util.RequestUtil;
import com.libqa.application.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 후 이전 URL로 핸들링 한다.
 * 사용자 아이디의 저장 유무를 확인하여 Cookie를 세팅한다.
 * @Author : yion
 * @Date : 2015. 4. 12.
 * @Description :
 */
@Slf4j
public class LoginHandler implements AuthenticationSuccessHandler {

    private static final String DEFAULT_URL = "/index";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String cookieMe = StringUtil.nullToString(request.getParameter("remember-me"), "N");
        String userEmail = StringUtil.nullToString(request.getParameter("userEmail"), "");

        setUserIdCookies(response, cookieMe, userEmail);


        RequestUtil.printRequest(request, "LoginHandler");
        String returnUrl = RequestUtil.refererUrl(request, DEFAULT_URL);
        ObjectMapper om = new ObjectMapper();

        Map<String, Object> map = new HashMap<String, Object>();
        log.info("### return URL = {}", returnUrl);
        map.put("status", true);
        map.put("returnUrl", returnUrl);

        // {"success" : true, "returnUrl" : "..."}
        String jsonString = om.writeValueAsString(map);

        OutputStream out = response.getOutputStream();
        out.write(jsonString.getBytes());
    }

    public void setUserIdCookies(HttpServletResponse response, String cookieMe, String userEmail) {
        if (cookieMe.equals("Y")) {
            Cookie cookie = new Cookie("SaveID", userEmail);
            cookie.setMaxAge(30 * 24 * 60 * 60);    // 30일
            response.addCookie(cookie);
        }
    }

}
