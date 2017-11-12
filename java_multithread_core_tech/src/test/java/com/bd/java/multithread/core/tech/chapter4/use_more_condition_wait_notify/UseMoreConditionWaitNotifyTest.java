package com.bd.java.multithread.core.tech.chapter4.use_more_condition_wait_notify;

import com.bd.java.multithread.core.tech.chapter4.use_more_condition_wait_notify.MyService;
import com.bd.java.multithread.core.tech.chapter4.use_more_condition_wait_notify.ThreadA;
import com.bd.java.multithread.core.tech.chapter4.use_more_condition_wait_notify.ThreadB;

public class UseMoreConditionWaitNotifyTest {
    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService();
        ThreadA t1 = new ThreadA(service);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(service);
        t2.setName("t2");
        t2.start();
        Thread.sleep(2000);
        service.signalA();
    }
}
