package com.bd.java.multithread.core.tech.chapter7.thread_state_timed_waiting;

import com.bd.java.multithread.core.tech.chapter7.thread_state_timed_waiting.MyThread;

public class ThreadStateTimedWaitingTest {
    public static void main(String[] args) {
        try {
            MyThread t = new MyThread();
            t.start();
            Thread.sleep(1000);
            System.out.println("t thread sleep() state " + t.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
