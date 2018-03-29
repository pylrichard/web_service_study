package com.bd.java.multithread.core.tech.chapter7.simple_date_format_ok;

import java.text.SimpleDateFormat;

public class SimpleDateFormatOkTest {
    private static int THREAD_NUM = 10;

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] dateStrArray = new String[] {
                "2000-01-01", "2000-01-02",
                "2000-01-03", "2000-01-04",
                "2000-01-05", "2000-01-06",
                "2000-01-07", "2000-01-08",
                "2000-01-09", "2000-01-10"
        };
        MyThread[] threads = new MyThread[THREAD_NUM];

        for (int i = 0; i < THREAD_NUM; i++) {
            threads[i] = new MyThread(sdf, dateStrArray[i]);
            threads[i].start();
        }
    }
}
