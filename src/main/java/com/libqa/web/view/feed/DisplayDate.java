package com.libqa.web.view.feed;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayDate {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String parse(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
}