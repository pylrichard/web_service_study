package com.bd.java_multithread_core_tech.chapter4.use_condition_wait_notify;

public class UseConditionWaitNotifyTest {
    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService();
        MyThread t = new MyThread(service);
        t.start();
        Thread.sleep(2000);
        service.signal();
    }
}
