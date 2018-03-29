package com.bd.java.multithread.core.tech.chapter7.thread_state_new;

public class MyThread extends Thread {
    public MyThread() {
        //显示main线程的状态为RUNNABLE
        System.out.println("constructor() main thread state " + Thread.currentThread().getState());
    }

    @Override
    public void run() {
        System.out.println("run() t thread state " + Thread.currentThread().getState());
    }
}
