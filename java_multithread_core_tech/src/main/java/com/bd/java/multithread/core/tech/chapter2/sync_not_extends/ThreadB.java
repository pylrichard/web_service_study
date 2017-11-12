package com.bd.java.multithread.core.tech.chapter2.sync_not_extends;

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
