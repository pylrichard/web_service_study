package com.bd.java_multithread_core_tech.chapter3.notify_hold_lock;

public class Service {
    public void waitMethod(Object lock) {
        try {
            synchronized (lock) {
                String name = Thread.currentThread().getName();
                System.out.println(name + " wait begin");
                lock.wait();
                System.out.println(name + " wait end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyMethod(Object lock) {
        try {
            synchronized (lock) {
                String name = Thread.currentThread().getName();
                System.out.println(name + " notify begin");
                //notify()不会立即释放锁，执行完notify()所在的同步代码块才会释放锁
                lock.notify();
                Thread.sleep(3000);
                System.out.println(name + " notify end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
