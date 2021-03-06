package com.bd.java.multithread.core.tech.chapter2.two_object_two_lock;

public class ThreadA extends Thread {
    private HasSelfPrivateNum numRef;

    public ThreadA(HasSelfPrivateNum numRef) {
        super();
        this.numRef = numRef;
    }

    @Override
    public void run() {
        super.run();
        numRef.addNum("a");
    }
}
