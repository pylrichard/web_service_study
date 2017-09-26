package com.bd.java_multithread_core_tech.chapter3.join_sleep_sync;

public class ThreadA extends Thread {
    private ThreadB tb;

    public ThreadA(ThreadB tb) {
        super();
        this.tb =tb;
    }

    @Override
    public void run() {
        try {
            synchronized (tb) {
                tb.start();
                System.out.println("ta run begin " + System.currentTimeMillis());
                //sleep()不会释放锁
                //Thread.sleep(4000);
                //join()会释放锁
                tb.join(5000);
                System.out.println("ta run end " + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
