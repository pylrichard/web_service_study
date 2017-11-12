package com.bd.java.multithread.core.tech.chapter2.sync_inner_class;

import com.bd.java.multithread.core.tech.chapter2.sync_inner_class.OutClass;

public class SyncInnerClassTest {
    public static void main(String[] args) {
        final OutClass.InnerClass inner = new OutClass.InnerClass();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                inner.method1();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                inner.method2();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
