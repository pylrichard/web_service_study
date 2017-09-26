package com.bd.java_multithread_core_tech.chapter2.sync_two_inner_class;

public class SyncTwoInnerClassTest {
    public static void main(String[] args) {
        final OutClass.InnerClass1 inner1 = new OutClass.InnerClass1();
        final OutClass.InnerClass2 inner2 = new OutClass.InnerClass2();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                inner1.method1(inner2);
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                inner1.method2();
            }
        }, "t2");

        //t3要等t1执行结束后才能获得inner2对象锁，执行method1()
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                inner2.method1();
            }
        }, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
