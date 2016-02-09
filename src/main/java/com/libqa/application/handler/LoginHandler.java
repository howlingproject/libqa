package com.libqa.application.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libqa.application.util.RequestUtil;
import com.libqa.application.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
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

    @Autowired
    protected AuthenticationManager authenticationManager;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        RequestUtil.printRequest(request, "* LoginHandler");


        //TODO root url 접근시 isLogin 값이 없어서 false 로 넘어가는 문제가 있음 (로그인 후에도 비로그인 메뉴로 출력되는 bug)

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


}
