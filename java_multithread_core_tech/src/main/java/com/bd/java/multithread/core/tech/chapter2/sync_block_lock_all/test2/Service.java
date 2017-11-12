package com.bd.java.multithread.core.tech.chapter2.sync_block_lock_all.test2;

public class Service {
    public void method(MyObject object) {
        synchronized (object) {
            try {
                System.out.println("service method get lock thread name = " + Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.println("service method release lock thread name = " + Thread.currentThread().getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
