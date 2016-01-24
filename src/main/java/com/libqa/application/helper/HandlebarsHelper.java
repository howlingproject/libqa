package com.libqa.application.helper;

import com.github.jknack.handlebars.Options;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import com.libqa.application.util.StringUtil;
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
        if( html == null ){
            return html;
        }
        Pattern pattern = Pattern.compile("(<[\\w\\W]+?>)");
        Matcher matcher = pattern.matcher(html);

        if(matcher.find()){
            html = matcher.replaceAll(" ");
        }

        return html;
    }

    public String subString(String html, int startIdx, int endIdx){
        if( html == null ){
            return html;
        }

        if( html.length() > endIdx ){
            return html.substring(startIdx, endIdx)+ "...";
        }
        return html;
    }

    public String length(String str, Options options) {
        return str.length() + "";
    }

    public String size(List<Object> list, Options options) {
        return list.size()+"";
    }

    public String nl2br(String str, Options options) {
        return null;
    }

    public String xif(String v1, String operator, String int2, Options options) {
        switch (operator) {
            case "==":
                return String.valueOf((v1.equals(int2)) ? Boolean.TRUE : null);

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
        if( obj1 == null && (obj2 == null || obj2.size() == 0) ){
            return options.inverse();
        }
        return options.fn();
    }

    public CharSequence compareTo(Object str1, Object str2, Options options) throws IOException {
        str1 = MoreObjects.firstNonNull(str1, "");
        str2 = MoreObjects.firstNonNull(str2, "");
        return (str1.equals(str2) ) ? options.fn(this) : options.inverse(this);
    }

    public CharSequence ifIntCont(Integer int1, String operator, Integer int2, Options options) throws Exception {
        int1 = MoreObjects.firstNonNull(int1, 0);
        int2 = MoreObjects.firstNonNull(int2, 0);
        operator = StringUtil.nullToString(operator);

        switch (operator) {
            case "==":
                return (int1 == int2) ? options.fn(this) : options.inverse(this);
            case "<":
                return (int1 < int2) ? options.fn(this) : options.inverse(this);
            case "<=":
                return (int1 <= int2) ? options.fn(this) : options.inverse(this);
            case ">":
                return (int1 > int2) ? options.fn(this) : options.inverse(this);
            case ">=":
                return (int1 >= int2) ? options.fn(this) : options.inverse(this);

            default:return options.inverse();
        }
    }

    public CharSequence ifIntArrCont(List<Object> list, String operator, Integer int2, Options options) throws Exception {
        if( Iterables.isEmpty(list) ){
            return options.inverse();
        }

        Integer int1 = Iterables.size(list);
        int2 = MoreObjects.firstNonNull(int2, 0);
        operator = StringUtil.nullToString(operator);

        switch (operator) {
            case "==":
                return (int1 == int2) ? options.fn(this) : options.inverse(this);
            case "<":
                return (int1 < int2) ? options.fn(this) : options.inverse(this);
            case "<=":
                return (int1 <= int2) ? options.fn(this) : options.inverse(this);
            case ">":
                return (int1 > int2) ? options.fn(this) : options.inverse(this);
            case ">=":
                return (int1 >= int2) ? options.fn(this) : options.inverse(this);

            default:return options.inverse();
        }
    }

    /**
     * keywordBadge
     *
     * @return keywordBadge
     * @throws Exception
     */
    public String keywordBadge(String keywordName){
        int keywordLength = keywordName.length();

        switch (keywordLength) {
            case 1:
                return "<span class=\"label label-primary\">" + keywordName +"</span>";
            case 2:
                return "<span class=\"label label-success\">" + keywordName +"</span>";
            case 3:
                return "<span class=\"label label-info\">" + keywordName +"</span>";
            case 4:
                return "<span class=\"label label-warning\">" + keywordName +"</span>";
            case 5:
                return "<span class=\"label label-danger\">" + keywordName +"</span>";
            case 6:
                return "<span class=\"label label-tag\">" + keywordName +"</span>";
            case 7:
                return "<span class=\"label label-focus\">" + keywordName +"</span>";
            case 8:
                return "<span class=\"label label-alert\">" + keywordName +"</span>";
            default :
                return "<span class=\"label label-default\">" + keywordName +"</span>";
        }
    }


}

