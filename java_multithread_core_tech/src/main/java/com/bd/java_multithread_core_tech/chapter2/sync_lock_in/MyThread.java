package com.bd.java_multithread_core_tech.chapter2.sync_lock_in;

public class MyThread extends Thread {
    @Override
    public void run() {
        super.run();
        Service service = new Service();
        service.service1();
    }
}
