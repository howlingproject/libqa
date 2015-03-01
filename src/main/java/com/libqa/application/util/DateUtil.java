package com.libqa.application.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yion on 2015. 3. 1..
 */
public class DateUtil extends DateUtils {

    /**
     * 현재일자를 반환한다.(형식 yyyyMMdd)
     *
     * @return 현재일자
     * @throws Exception
     */
    public static String getToday() throws Exception {
        return getDate(new Date(), "yyyyMMdd");
    }

    /**
     * 현재일시간을 반환한다.(형식 yyyy-MM-dd HH:mm:ss)
     * @return
     * @throws Exception
     */
    public static String getTodayTimeStamp() throws Exception{
        return getDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 현재시간을 반환한다.(형식 yyyy.MM.dd hh:mm:ss)
     *
     * @return 현재 시간
     * @throws Exception
     */
    public static String getTodayTime() {
        return getDate(new Date(), "yyyy.MM.dd HH:mm:ss");
    }

    /**
     * 현재 시간을 반환한다.(형식 yyyyMMddHHmmss);
     * @return
     * @throws Exception
     */
    public static String getCurrentTime() throws Exception {
        Calendar dateTime = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = formatter.format(dateTime.getTime());

        return str;
    }

    /**
     * 현재시간을 반환한다.(형식 yyyy/MM/dd hh:mm:ss)
     *
     * @return 현재 시간
     * @throws Exception
     */
    public static String getTodayTimeForDB() throws Exception {
        return getDate(new Date(), "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 현재일자를 지정한 형식으로 반환한다.
     *
     * @return 현재일자
     * @throws Exception
     */
    public static String getToday(String pFormat) throws Exception {
        return getDate(new Date(), pFormat);
    }

    /**
     * Date 타입의 날짜를 지정한 형식의 문자형 날짜로 반환한다.
     *
     * @param pDate
     *            Date 객체
     * @param pFormat
     *            SimpleDateFormat에 정의된 날짜형식
     * @return 변경된 날짜
     */
    public static String getDate(Date pDate, String pFormat) {

        if (pDate == null)
            return "";

        StringBuffer ret = new StringBuffer();
        new SimpleDateFormat(pFormat).format(pDate, ret, new FieldPosition(0));
        return ret.toString();
    }

}
