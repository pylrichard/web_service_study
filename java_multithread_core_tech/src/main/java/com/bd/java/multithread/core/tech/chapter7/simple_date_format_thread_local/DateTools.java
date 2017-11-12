package com.bd.java.multithread.core.tech.chapter7.simple_date_format_thread_local;

import java.text.SimpleDateFormat;

public class DateTools {
    private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>();

    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        SimpleDateFormat sdf = tl.get();

        if (sdf == null) {
            sdf = new SimpleDateFormat(pattern);
            tl.set(sdf);
        }

        return sdf;
    }
}
