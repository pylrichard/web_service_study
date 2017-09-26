package com.bd.java_multithread_core_tech.chapter5.timer_more_task;

import java.util.TimerTask;

public class MyTimerTask2 extends TimerTask {
    @Override
    public void run() {
        System.out.println("MyTimerTask2");
        //TimerTask.cancel()将自身从任务队列中删除，其它任务不受影响
        //Timer.cancel()将任务队列中所有任务删除
        this.cancel();
    }
}
