package com.libqa.application.helper;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songanji on 2015. 4. 5..
 */
@Slf4j
public class HandlebarsHelper {

    public String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public String nullToNumber(Integer num, Options options) {
        if (num == null) {
            return "0";
        }
        return num.toString();
    }

    public String htmlDelete(String html, Options options) {
        Pattern pattern = Pattern.compile("(<[\\w\\W]+?>)");
        Matcher matcher = pattern.matcher(html);

        if(matcher.find()){
            html = matcher.replaceAll(" ");
        }

        return html;
    }

    public String subString(String html, int startIdx, int endIdx){
        if( html.length() > endIdx ){
            return html.substring(startIdx, endIdx)+ "...";
        }
        return html;
    }

    public String length(String str, Options options) {
        return str.length()+"";
    }

    public String nl2br(String str, Options options) {
        return null;
    }

    public String xif(String v1, String operator, String v2, Options options) {
        switch (operator) {
            case "==":
                return String.valueOf((v1.equals(v2)) ? Boolean.TRUE : null);

            default:
                return null;
        }
    }

}

