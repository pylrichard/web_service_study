package com.bd.java.multithread.core.tech.chapter2.throw_exception_no_lock;

public class ThrowExceptionNoLockTest {
    public static void main(String[] args) {
        try {
            Service service = new Service();
            ThreadA t1 = new ThreadA(service);
            t1.setName("t1");
            t1.start();
            Thread.sleep(200);
            ThreadB t2 = new ThreadB(service);
            t2.setName("t2");
            t2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
