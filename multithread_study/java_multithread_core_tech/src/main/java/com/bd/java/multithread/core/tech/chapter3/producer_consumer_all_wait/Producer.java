package com.bd.java.multithread.core.tech.chapter3.producer_consumer_all_wait;

public class Producer {
    private String lock;

    public Producer(String lock) {
        super();
        this.lock = lock;
    }

    public void setValue() {
        try {
            synchronized (lock) {
                String name = Thread.currentThread().getName();
                while (!MyString.isEmpty()) {
                    System.out.println("producer " + name + " waiting");
                    lock.wait();
                }

                System.out.println("producer " + name +" running");
                String str = System.currentTimeMillis() + "_" + System.nanoTime();
                MyString.setStr(str);
                lock.notify();
                //lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
