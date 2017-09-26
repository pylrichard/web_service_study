package com.bd.java_multithread_core_tech.chapter5.timer_schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerScheduleTest {
    private static Timer timer = new Timer();
    private static int runCount = 0;

    public static class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                System.out.println("begin time " + new Date());
                Thread.sleep(1000);
//                Thread.sleep(4000);
                System.out.println("end time " + new Date());
                runCount++;
                if (runCount == 5) {
                    timer.cancel();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        try {
            MyTimerTask task = new MyTimerTask();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = "2017-08-17 23:42:40";
            Date time = sdf.parse(dateStr);
            //定时任务执行时长没有超过时间间隔，下一次任务执行开始时间=上一次任务执行开始时间+时间间隔
            //定时任务执行时长超过时间间隔，下一次任务执行开始时间=上一次任务执行结束时间
//            timer.schedule(task, time, 2000);
            //下一次任务执行开始时间=上一次任务执行结束时间
            timer.scheduleAtFixedRate(task, time, 5000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
