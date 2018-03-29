package com.bd.java.multithread.core.tech.chapter3.join_exception;

public class ThreadC extends Thread {
    private ThreadB tb;

    public ThreadC(ThreadB tb) {
        super();
        this.tb = tb;
    }

    @Override
    public void run() {
        //join()和interrupt()遇到，会触发异常，ThreadA线程还在继续运行，未出现异常
        tb.interrupt();
    }
}
