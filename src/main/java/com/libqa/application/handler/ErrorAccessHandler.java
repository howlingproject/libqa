package com.libqa.application.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : yion
 * @Date : 2015. 4. 19.
 * @Description :
 */
public class ErrorAccessHandler implements AccessDeniedHandler {

    @Setter
    @Getter
    private String errorPage;

    public ErrorAccessHandler() {

    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("### Access Denied Handler !!");
        response.sendRedirect(errorPage);
    }
}
