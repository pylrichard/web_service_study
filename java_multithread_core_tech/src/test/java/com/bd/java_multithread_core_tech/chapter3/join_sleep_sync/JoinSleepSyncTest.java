package com.bd.java_multithread_core_tech.chapter3.join_sleep_sync;

public class JoinSleepSyncTest {
    public static void main(String[] args) {
        try {
            ThreadB tb = new ThreadB();
            ThreadA ta = new ThreadA(tb);
            ta.start();
            Thread.sleep(1000);
            ThreadC tc = new ThreadC(tb);
            tc.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
