package com.bd.java.multithread.core.tech.chapter7.thread_state_waiting;

public class MyThread extends Thread {
    @Override
    public void run() {
        try {
            Byte lock = Lock.getLock();
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
