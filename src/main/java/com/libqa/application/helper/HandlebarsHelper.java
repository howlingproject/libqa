package com.libqa.application.helper;

import com.github.jknack.handlebars.Options;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by songanji on 2015. 4. 5..
 */
public class HandlebarsHelper {

    public String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public String nullToNumber(String num, Options options) {
        if (num == null) {
            return "0";
        }
        return num;
    }

    public String nl2br(String str, Options options) {
        return null;
    }

}

