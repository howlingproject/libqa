package com.libqa.application.util;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by yion on 2015. 3. 1..
 */
public class StringUtil extends StringUtils {

    public static String nullToString(String str) {
        return nullToString(str, "");
    }

    public static String nullToString(String str, String defaultStr) {
        if (str == null) {
            return defaultStr;
        }
        return str;
    }

    public static String abbreviateString(String str) {
        return StringUtils.defaultIfEmpty(str, "...");
    }

}
