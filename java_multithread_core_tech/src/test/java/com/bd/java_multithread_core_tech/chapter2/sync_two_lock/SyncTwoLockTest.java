package com.bd.java_multithread_core_tech.chapter2.sync_two_lock;

public class SyncTwoLockTest {
    public static void main(String[] args) {
        Service service = new Service();
        ThreadA t1 = new ThreadA(service);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(service);
        t2.setName("t2");
        t2.start();
        //调用methodC()执行结果是异步的，因为持有不同的锁，一个对象锁，一个Class锁
        //Class锁对所有类对象起作用
        ThreadC t3 = new ThreadC(service);
        t3.setName("t3");
        t3.start();
    }
}
