package com.bd.java.multithread.core.tech.chapter2.sync_block_class_lock;

import com.bd.java.multithread.core.tech.chapter2.sync_block_class_lock.Service;
import com.bd.java.multithread.core.tech.chapter2.sync_block_class_lock.ThreadA;
import com.bd.java.multithread.core.tech.chapter2.sync_block_class_lock.ThreadB;

public class SyncBlockClassLockTest {
    public static void main(String[] args) {
        //synchronized (class)代码块和synchronized static方法作用是一样的
        Service service1 = new Service();
        Service service2 = new Service();

        ThreadA t1 = new ThreadA(service1);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(service2);
        t2.setName("t2");
        t2.start();
    }
}
