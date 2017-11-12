package com.bd.java.multithread.core.tech.chapter5.timer_more_task;

import java.util.TimerTask;

public class MyTimerTask1 extends TimerTask {
    @Override
    public void run() {
        try {
            System.out.println("MyTimerTask1");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
