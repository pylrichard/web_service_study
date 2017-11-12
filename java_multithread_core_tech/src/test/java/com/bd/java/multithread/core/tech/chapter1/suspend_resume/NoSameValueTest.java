package com.bd.java.multithread.core.tech.chapter1.suspend_resume;

import com.bd.java.multithread.core.tech.chapter1.suspend_resume.NoSameValue;

public class NoSameValueTest {
    public static void main(String[] args) throws InterruptedException {
        final NoSameValue obj = new NoSameValue();

        Thread t1 = new Thread() {
            public void run() {
                obj.setValue("b", "bb");
            }
        };
        t1.setName("t1");
        t1.start();
        Thread.sleep(500);

        Thread t2 = new Thread() {
            public void run() {
                obj.print();
            }
        };
        t2.start();
    }
}
