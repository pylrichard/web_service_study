package com.bd.java.multithread.core.tech.chapter5.timer;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    @Override
    public void run() {
        System.out.println("MyTimerTask");
    }
}
