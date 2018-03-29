package com.bd.java.multithread.core.tech.chapter3.thread_local_3;

import java.util.Date;

public class Tools {
    public static ThreadLocal<Date> tl = new ThreadLocal<Date>();

    public static void setTL(Date date) {
        tl.set(date);
    }

    public static Date getTL() {
        return tl.get();
    }
}
