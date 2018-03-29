package com.bd.java.multithread.core.tech.chapter4.use_condition_wait_notify;

public class UseConditionWaitNotifyTest {
    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService();
        MyThread t = new MyThread(service);
        t.start();
        Thread.sleep(2000);
        service.signal();
    }
}
