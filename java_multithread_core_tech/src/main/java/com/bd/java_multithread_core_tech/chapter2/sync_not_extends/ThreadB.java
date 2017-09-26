package com.bd.java_multithread_core_tech.chapter2.sync_not_extends;

public class ThreadB extends Thread {
    private Sub sub;

    public ThreadB(Sub sub) {
        super();
        this.sub = sub;
    }

    @Override
    public void run() {
        sub.method();
    }
}
