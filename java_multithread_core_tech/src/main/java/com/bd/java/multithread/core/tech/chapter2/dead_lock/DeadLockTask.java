package com.bd.java.multithread.core.tech.chapter2.dead_lock;

public class DeadLockTask implements Runnable {
    public String name;
    public Object lock1 = new Object();
    public Object lock2 = new Object();

    public void setName(String name) {
        this.name = name;
    }

    public void run() {
        if (name.equals("t1")) {
            synchronized (lock1) {
                try {
                    System.out.println("thread name = " + name + " get lock1");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock2) {
                    System.out.println("thread name = " + name + " get lock2");
                }
            }
        }

        if (name.equals("t2")) {
            synchronized (lock2) {
                try {
                    System.out.println("thread name = " + name + " get lock2");
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock1) {
                    System.out.println("thread name = " + name + " get lock1");
                }
            }
        }
    }
}
