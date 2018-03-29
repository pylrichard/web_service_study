package com.bd.java.multithread.core.tech.chapter2.sync_static_method;

public class ThreadA extends Thread {
    @Override
    public void run() {
        Service.methodA();
    }
}
