package com.libqa.application.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : yion
 * @Date : 2015. 4. 19.
 * @Description :
 */
public class RequestUtil {

    public static String refererUrl(HttpServletRequest request, String defaultUrl) {
        String returnUrl = request.getHeader("Referer");

        if (returnUrl == null) {
            returnUrl = defaultUrl;
        }
        return returnUrl;
    }

}
