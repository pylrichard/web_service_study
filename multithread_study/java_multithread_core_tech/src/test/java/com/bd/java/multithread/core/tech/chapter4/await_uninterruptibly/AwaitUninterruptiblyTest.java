package com.bd.java.multithread.core.tech.chapter4.await_uninterruptibly;

public class AwaitUninterruptiblyTest {
    public static void main(String[] args) {
        try {
            MyService service = new MyService();
            MyThread t = new MyThread(service);
            t.start();
            Thread.sleep(2000);
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
