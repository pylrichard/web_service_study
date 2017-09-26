package com.bd.java_multithread_core_tech.chapter3.join_sleep_sync;

public class ThreadC extends Thread {
    private ThreadB tb;

    public ThreadC(ThreadB tb) {
        super();
        this.tb = tb;
    }

    @Override
    public void run() {
        //tc要等到ta休眠完毕，释放tb锁后才能调用同步方法
        //ta使用join()，tc可以调用同步方法
        tb.method();
    }
}
