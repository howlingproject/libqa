package com.libqa.application.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author : yion
 * @Date : 2015. 4. 19.
 * @Description :
 */

@Slf4j
public class RequestUtil {

    /**
     * 이전 페이지의 referer 를 조회한다. 특정 패턴일 경우 /index를 리턴한다.
     * @param request
     * @param defaultUrl
     * @return
     */
    public static String refererUrl(HttpServletRequest request, String defaultUrl) {
        String returnUrl = request.getHeader("Referer");
        log.info("### returnUrl = {}", returnUrl);

        return checkReturnUrl(returnUrl);
    }

    /**
     * 로그인 후 이전 페이지의 성격에 따라 index 페이지 리다이렉트 URL을 결정한다.
     * @param returnUrl
     * @return
     */
    public static String checkReturnUrl(String returnUrl) {
        if (returnUrl != null) {
            int subPoint = returnUrl.lastIndexOf("/");
            String subUrl = returnUrl.substring(subPoint, returnUrl.length());
            log.debug("###################### subUrl : {}", subUrl);

            if (subUrl.equals("/") || subUrl.equals("/loginPage") || subUrl.equals("/signUp")) {
                returnUrl = "/index";
            }
        } else {
            returnUrl = "/index";
        }

        return returnUrl;
    }


    /**
     * request 에 적재된 element를 출력한다.
     * @param request
     */
    public static void printRequest(HttpServletRequest request, String callByName) {
        Enumeration enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String name = (String) enumer.nextElement();
            String values[] = request.getParameterValues(name);

            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    log.info("** LoginHandler " + name + "( " + i + " ) " + values[i]);
                }
            }
        }

    }



}
