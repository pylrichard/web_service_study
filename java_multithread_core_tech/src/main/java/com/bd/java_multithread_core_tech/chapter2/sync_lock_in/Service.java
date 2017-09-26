package com.bd.java_multithread_core_tech.chapter2.sync_lock_in;

public class Service {
    //可重入锁表示1个线程获得了某个对象锁，此时对象锁还没有释放，想再次获取此对象锁时是可以的
    //锁不可重入的话，就会造成死锁
    synchronized public void service1() {
        System.out.println("service1");
        service2();
    }

    synchronized public void service2() {
        System.out.println("service2");
        service3();
    }

    synchronized public void service3() {
        System.out.println("service3");
    }
}
