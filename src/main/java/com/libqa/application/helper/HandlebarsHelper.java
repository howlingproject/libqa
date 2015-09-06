package com.libqa.application.helper;

import com.github.jknack.handlebars.Options;
import com.libqa.web.domain.Wiki;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

        System.out.println("############### length ############### ");
        return str.length() + "";
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

    public String keywordLink(String keywordType, String keywordName) {
        if (keywordType == null) {
            return "#";
        }else if( "WIKI".equals(keywordType) ){
            return "/wiki/list/keyword/"+keywordName;
        }

        return "#";
    }

    public CharSequence ifWikiRelation(Wiki obj1, List<Wiki> obj2, Options options) throws IOException {
        if( obj1 == null && obj2 == null ){
            return options.inverse();
        }
        return options.fn();
    }

    public CharSequence compareTo(Object str1, Object str2, Options options) throws IOException {
        if( str2.equals(str1) ){
            return options.fn();
        }
        return options.inverse();
    }


}

