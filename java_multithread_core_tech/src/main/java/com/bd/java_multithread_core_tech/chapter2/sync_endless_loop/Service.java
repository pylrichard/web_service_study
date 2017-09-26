package com.bd.java_multithread_core_tech.chapter2.sync_endless_loop;

public class Service {
    Object object1 = new Object();
    Object object2 = new Object();

    synchronized public void methodA() {
        System.out.println("methodA begin");
        //此处死循环会造成A线程一直持有this对象锁，B线程没有执行机会
        while (true) {}
    }

    synchronized public void methodB() {
        System.out.println("methodB begin");
        System.out.println("methodB end");
    }

    public void methodC() {
        //使用同步代码块对不同的对象进行同步来解决
        synchronized (object1) {
            System.out.println("methodC begin");
            while (true) {
            }
        }
    }

    public void methodD() {
        synchronized (object2) {
            System.out.println("methodD begin");
            System.out.println("methodD end");
        }
    }
}
