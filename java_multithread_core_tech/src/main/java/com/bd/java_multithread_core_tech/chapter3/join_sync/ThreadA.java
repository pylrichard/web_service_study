package com.bd.java_multithread_core_tech.chapter3.join_sync;

public class ThreadA extends Thread {
    private ThreadB tb;

    public ThreadA(ThreadB tb) {
        super();
        this.tb = tb;
    }

    @Override
    public void run() {
        try {
            synchronized (tb) {
                System.out.println("ta run begin");
                Thread.sleep(5000);
                System.out.println("ta run end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
