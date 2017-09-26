package com.bd.java_multithread_core_tech.chapter2.two_object_two_lock;

public class TwoObjectTwoLockTest {
    public static void main(String[] args) {
        HasSelfPrivateNum numRef1 = new HasSelfPrivateNum();
        HasSelfPrivateNum numRef2 = new HasSelfPrivateNum();

        //两个线程分别访问同一个类的不同对象的相同名称的同步方法，以异步方式运行
        //多个线程访问同一个对象的同步方法时，哪个线程先执行，就持有该对象的锁，其它线程进行等待
        //多个线程访问同一个类的不同对象，JVM会创建多个锁
        ThreadA t1 = new ThreadA(numRef1);
        t1.start();
        ThreadB t2 = new ThreadB(numRef2);
        t2.start();
    }
}
