package com.bd.java.multithread.core.tech.chapter2.sync_lock_in;

public class MyThread extends Thread {
    @Override
    public void run() {
        super.run();
        Service service = new Service();
        service.service1();
    }
}
