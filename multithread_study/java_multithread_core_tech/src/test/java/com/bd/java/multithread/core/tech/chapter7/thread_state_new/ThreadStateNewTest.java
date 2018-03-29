package com.bd.java.multithread.core.tech.chapter7.thread_state_new;

public class ThreadStateNewTest {
    public static void main(String[] args) {
        try {
            MyThread t = new MyThread();
            System.out.println("t thread new() state " + t.getState());
            t.start();
            Thread.sleep(1000);
            System.out.println("t thread end state " + t.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
