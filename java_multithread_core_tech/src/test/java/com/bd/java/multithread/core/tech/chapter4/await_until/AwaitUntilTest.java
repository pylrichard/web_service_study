package com.bd.java.multithread.core.tech.chapter4.await_until;

import com.bd.java.multithread.core.tech.chapter4.await_until.MyService;
import com.bd.java.multithread.core.tech.chapter4.await_until.ThreadA;
import com.bd.java.multithread.core.tech.chapter4.await_until.ThreadB;

public class AwaitUntilTest {
    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService();
        ThreadA t1 = new ThreadA(service);
        //不启动t2，5s后t1自动唤醒
        t1.start();
        Thread.sleep(2000);
        ThreadB t2 = new ThreadB(service);
        //启动t2，在t1等待时间到达之前唤醒t1
        t2.start();
    }
}
