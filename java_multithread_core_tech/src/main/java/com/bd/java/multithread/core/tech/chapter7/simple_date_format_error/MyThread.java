package com.bd.java.multithread.core.tech.chapter7.simple_date_format_error;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread {
    private SimpleDateFormat sdf;
    private String dateStr;

    public MyThread(SimpleDateFormat sdf, String dateStr) {
        super();
        this.sdf = sdf;
        this.dateStr = dateStr;
    }

    @Override
    public void run() {
        try {
            Date date = sdf.parse(dateStr);
            String parseDateStr = sdf.format(date);
            if (!parseDateStr.equals(dateStr)) {
                System.out.println("thread name " + this.getName()
                                    + " dateStr " + dateStr
                                    + " convert to parseDateStr " + parseDateStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
