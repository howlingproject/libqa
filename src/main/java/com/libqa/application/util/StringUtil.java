package com.libqa.application.util;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by yion on 2015. 3. 1..
 */
public class StringUtil extends StringUtils {

    public static String convertString(Object object){
        return convertString(object, null);
    }

    public static String convertString(Object object, String defaultValue){
        if( object == null ){
            if( defaultValue == null ){
                return  "";
            }else{
                return  defaultValue;
            }

        }
        return (String)object;
    }

    public static String nullToString(String str){
        return nullToString(str, "");
    }

    public static String nullToString(String str, String defaultStr){
        if( str == null ){
            return defaultStr;
        }
        return str;
    }

}
