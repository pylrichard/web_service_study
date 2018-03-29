package com.bd.java.multithread.core.tech.chapter1.suspend_resume;

public class SuspendResumeTest {
    public static void main(String[] args) {
        try {
            SuspendResume t = new SuspendResume();
            t.start();
            Thread.sleep(5000);

            t.suspend();
            System.out.println("A = " + System.currentTimeMillis() + " i=" + t.getI());
            Thread.sleep(5000);
            System.out.println("A = " + System.currentTimeMillis() + " i=" + t.getI());

            t.resume();
            Thread.sleep(5000);

            t.suspend();
            System.out.println("B = " + System.currentTimeMillis() + " i=" + t.getI());
            Thread.sleep(5000);
            System.out.println("B = " + System.currentTimeMillis() + " i=" + t.getI());
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
