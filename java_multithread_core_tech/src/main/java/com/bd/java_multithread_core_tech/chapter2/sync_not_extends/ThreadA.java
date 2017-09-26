package com.bd.java_multithread_core_tech.chapter2.sync_not_extends;

public class ThreadA extends Thread {
    private Sub sub;

    public ThreadA(Sub sub) {
        super();
        this.sub = sub;
    }

    @Override
    public void run() {
        sub.method();
    }
}
