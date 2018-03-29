package com.bd.java.multithread.core.tech.chapter2.sync_not_extends;

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
