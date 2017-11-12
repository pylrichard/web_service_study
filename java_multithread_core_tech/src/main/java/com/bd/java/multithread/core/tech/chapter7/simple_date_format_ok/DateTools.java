package com.bd.java.multithread.core.tech.chapter7.simple_date_format_ok;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {
    /**
     * 解决转换错误就是创建多个SimpleDateFormat实例
     */
    public static Date parse(String pattern, String dateStr) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    public static String format(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
