package com.libqa.application.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libqa.application.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 후 이전 URL로 핸들링 한다.
 *
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
        // 여기서 회원 정보 어노테이션 추가함
        printRequest(request);
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

    private void printRequest(HttpServletRequest request) {
        Enumeration enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String name = (String) enumer.nextElement();
            String values[] = request.getParameterValues(name);

            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    System.out.println("** " + name + "( " + i + " )" + values[i]);
                }
            }
        }

    }


}
