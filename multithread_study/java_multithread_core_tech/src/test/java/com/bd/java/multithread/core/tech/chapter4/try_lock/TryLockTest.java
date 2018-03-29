package com.bd.java.multithread.core.tech.chapter4.try_lock;

public class TryLockTest {
    public static void main(String[] args) {
        final MyService service = new MyService();
        Runnable runnable = new Runnable() {
            public void run() {
                service.tryLockTimeOut();
            }
        };

        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        t1.start();
        Thread t2 = new Thread(runnable);
        t2.setName("t2");
        t2.start();
    }
}
