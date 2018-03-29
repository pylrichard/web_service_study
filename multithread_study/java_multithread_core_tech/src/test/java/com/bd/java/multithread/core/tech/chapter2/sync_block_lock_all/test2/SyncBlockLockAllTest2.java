package com.bd.java.multithread.core.tech.chapter2.sync_block_lock_all.test2;

public class SyncBlockLockAllTest2 {
    public static void main(String[] args) throws InterruptedException {
        Service service = new Service();
        MyObject object = new MyObject();

        ThreadA t1 = new ThreadA(service, object);
        t1.setName("t1");
        t1.start();
        Thread.sleep(1000);
        ThreadB t2 = new ThreadB(service, object);
        t2.setName("t2");
        t2.start();
    }
}
