
package com.wangtianya.yaa.other;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

    public static String DateToString(Date date, String formatStr) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatStr, Locale.getDefault());
        return format.format(date);
    }

    public static Date StringToDate(String formatStr, String timeStr) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatStr, Locale.getDefault());
        try {
            return (Date) format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String LongToString(Long l) {
        return TimeUtil.DateToString(new Date(l), DATE_TIME_FORMAT);
    }

    public static java.text.SimpleDateFormat getFormat(String formatStr) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatStr, Locale.getDefault());
        return format;
    }
}
