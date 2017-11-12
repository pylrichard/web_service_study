package com.bd.java.multithread.core.tech.chapter3.join_sleep_sync;

import com.bd.java.multithread.core.tech.chapter3.join_sleep_sync.ThreadA;
import com.bd.java.multithread.core.tech.chapter3.join_sleep_sync.ThreadB;
import com.bd.java.multithread.core.tech.chapter3.join_sleep_sync.ThreadC;

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
