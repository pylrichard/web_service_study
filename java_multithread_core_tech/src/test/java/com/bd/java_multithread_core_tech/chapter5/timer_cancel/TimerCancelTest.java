package com.bd.java_multithread_core_tech.chapter5.timer_cancel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerCancelTest {
    private static int i = 0;

    public static class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("i = " + i);
        }
    }

    public static void main(String[] args) {
        while (true) {
            try {
                i++;
                Timer timer = new Timer();
                MyTimerTask task = new MyTimerTask();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = "2017-08-17 23:42:40";
                Date time = sdf.parse(dateStr);
                timer.schedule(task, time);
                //cancel()不一定能清除任务，因为没有获取到队列锁，TimerTask任务正常执行
                timer.cancel();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
