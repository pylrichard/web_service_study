package com.bd.java_multithread_core_tech.chapter2.sync_block_lock_all.test1;

public class Service {
    public void method(MyObject object) {
        synchronized (object) {
            try {
                System.out.println("get lock thread name = " + Thread.currentThread().getName());
                Thread.sleep(2000);
                System.out.println("release lock thread name = " + Thread.currentThread().getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
