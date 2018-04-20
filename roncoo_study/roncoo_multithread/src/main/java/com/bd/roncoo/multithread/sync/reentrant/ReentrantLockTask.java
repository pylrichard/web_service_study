package com.bd.roncoo.multithread.sync.reentrant;

import java.util.concurrent.locks.Lock;

public class ReentrantLockTask {
    private int value;
    //    Lock lock = new ReentrantLock();
    Lock lock = new MyReentrantLock();

    public static void main(String[] args) {
        ReentrantLockTask task = new ReentrantLockTask();
        new Thread(task::run).start();
        new Thread(task::run).start();
        new Thread(task::run).start();
    }

    public void run() {
        while (true) {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " " + value++);
            lock.unlock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
