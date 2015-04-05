package com.libqa.application.Helper;

import com.github.jknack.handlebars.Options;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by songanji on 2015. 4. 5..
 */
public class HowlingHelper {

    public String formatDate(Date date, String pattern) {
        if( date == null ){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

}
