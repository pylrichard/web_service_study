package com.bd.java.multithread.core.tech.chapter1.suspend_resume;

import com.bd.java.multithread.core.tech.chapter1.suspend_resume.LockStop;

public class LockStopTest {
    public static void main(String[] args) {
        try {
            LockStop t = new LockStop();
            t.start();
            Thread.sleep(1000);
            t.suspend();
            System.out.println("main end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
