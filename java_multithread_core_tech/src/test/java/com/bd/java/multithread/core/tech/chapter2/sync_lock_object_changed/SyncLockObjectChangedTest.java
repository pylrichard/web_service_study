package com.bd.java.multithread.core.tech.chapter2.sync_lock_object_changed;

import com.bd.java.multithread.core.tech.chapter2.sync_lock_object_changed.Service;
import com.bd.java.multithread.core.tech.chapter2.sync_lock_object_changed.ThreadA;
import com.bd.java.multithread.core.tech.chapter2.sync_lock_object_changed.ThreadB;

public class SyncLockObjectChangedTest {
    public static void main(String[] args) throws InterruptedException {
        Service service = new Service();
        ThreadA t1 = new ThreadA(service);
        t1.setName("t1");
        ThreadB t2 = new ThreadB(service);
        t2.setName("t2");
        t1.start();
        //休眠50ms让t1修改lock的值为"456"，B线程获取的lock为"456"，A线程获取的lock为"123"
        //所以执行结果是异步的
        //
        //注释休眠语句后，A和B线程持有的lock都是"123"
        //虽然lock被修改成了"456"，但A和B线程获取同步锁时lock为"123"
        //所以执行结果是同步的
        //Thread.sleep(50);
        t2.start();
    }
}
