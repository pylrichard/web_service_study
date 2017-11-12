package com.bd.java.multithread.core.tech.chapter2.thread3;

public class ThreadA extends Thread {
    private PublicVar var;

    public ThreadA(PublicVar var) {
        this.var = var;
    }

    @Override
    public void run() {
        super.run();
        var.setValue("B", "BB");
    }
}
