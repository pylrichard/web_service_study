package com.bd.java.multithread.core.tech.chapter5.timer_more_task;

import com.bd.java.multithread.core.tech.chapter5.timer_more_task.MyTimerTask1;
import com.bd.java.multithread.core.tech.chapter5.timer_more_task.MyTimerTask2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class TimerMoreTaskTest {
    public static void main(String[] args) {
        Timer timer = new Timer();

        try {
            //TimerTask是以队列的方式一个个顺序执行的
            MyTimerTask1 task1 = new MyTimerTask1();
            MyTimerTask2 task2 = new MyTimerTask2();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //即使设置为同一时间执行，但task1执行耗时3s，task2执行延时3s
            String dateStr1 = "2017-08-17 23:28:00";
            Date time1 = sdf.parse(dateStr1);
            timer.schedule(task1, time1, 4000);
            String dateStr2 = "2017-08-17 23:28:00";
            Date time2 = sdf.parse(dateStr2);
            timer.schedule(task2, time2, 4000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
