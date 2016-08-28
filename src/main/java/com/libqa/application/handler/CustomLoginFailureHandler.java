package com.libqa.application.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yong on 2015-08-31.
 *
 * @author Yong
 */
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = "";

        if(exception.getClass() == UsernameNotFoundException.class) {
            message = "cannot find a user";
        } else if(exception.getClass() == BadCredentialsException.class) {
            message = "check your password";
        }
        ObjectMapper om = new ObjectMapper();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resultCode", -1);
        map.put("message", message);

        // {"success" : false, "message" : "..."}
        String jsonString = om.writeValueAsString(map);

        OutputStream out = response.getOutputStream();
        out.write(jsonString.getBytes());
    }
}
