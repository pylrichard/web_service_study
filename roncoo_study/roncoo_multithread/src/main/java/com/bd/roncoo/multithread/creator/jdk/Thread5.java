package com.bd.roncoo.multithread.creator.jdk;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时任务
 */
public class Thread5 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask run()");
            }
        }, 0, 1000);
    }
}
