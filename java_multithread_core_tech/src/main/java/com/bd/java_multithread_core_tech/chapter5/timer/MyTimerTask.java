package com.bd.java_multithread_core_tech.chapter5.timer;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    @Override
    public void run() {
        System.out.println("MyTimerTask");
    }
}
