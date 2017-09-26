package com.bd.java_multithread_core_tech.chapter7.thread_state_waiting;

public class ThreadStateWaitingTest {
    public static void main(String[] args) {
        try {
            MyThread t = new MyThread();
            t.start();
            Thread.sleep(1000);
            System.out.println("t thread state " + t.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
