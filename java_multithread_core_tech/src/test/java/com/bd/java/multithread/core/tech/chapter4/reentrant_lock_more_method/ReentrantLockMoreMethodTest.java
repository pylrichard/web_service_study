package com.bd.java.multithread.core.tech.chapter4.reentrant_lock_more_method;

import com.bd.java.multithread.core.tech.chapter4.reentrant_lock_more_method.MyService;
import com.bd.java.multithread.core.tech.chapter4.reentrant_lock_more_method.ThreadA;
import com.bd.java.multithread.core.tech.chapter4.reentrant_lock_more_method.ThreadB;

public class ReentrantLockMoreMethodTest {
    public static void main(String[] args) {
        MyService service = new MyService();
        ThreadA t1 = new ThreadA(service);
        t1.setName("t1");
        t1.start();
        ThreadA t2 = new ThreadA(service);
        t2.setName("t2");
        t2.start();
        ThreadB t3 = new ThreadB(service);
        t3.setName("t3");
        t3.start();
        ThreadB t4 = new ThreadB(service);
        t4.setName("t4");
        t4.start();
    }
}
