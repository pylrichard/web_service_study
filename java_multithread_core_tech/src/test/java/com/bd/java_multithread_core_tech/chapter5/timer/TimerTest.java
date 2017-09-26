package com.bd.java_multithread_core_tech.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class TimerTest {
    public static void main(String[] args) {
        //创建Timer就是启动一个新的线程，新线程以用户线程运行，执行TimerTask的任务后，线程不会结束
        Timer timer = new Timer();
        //设置以守护线程运行，TimerTask的任务不会运行，TimerTest进程启动后很快结束，守护线程也结束
//        Timer timer = new Timer(true);

        try {
            MyTimerTask task = new MyTimerTask();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = "2017-08-17 23:42:40";
            Date time = sdf.parse(dateStr);
//            timer.schedule(task, time);
            //按周期(4s)无限循环执行定时任务
//            timer.schedule(task, time, 4000);
            //以调用schedule()的当前时间为参考时间，延迟指定毫秒数执行一次定时任务
//            timer.schedule(task, 3000);
            //以调用schedule()的当前时间为参考时间，延迟指定毫秒数，再以指定的时间间隔无限循环执行定时任务
            timer.schedule(task, 3000, 2000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
