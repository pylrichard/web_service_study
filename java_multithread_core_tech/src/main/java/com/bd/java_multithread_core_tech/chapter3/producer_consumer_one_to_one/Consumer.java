package com.bd.java_multithread_core_tech.chapter3.producer_consumer_one_to_one;

public class Consumer {
    private String lock;

    public Consumer(String lock) {
        super();
        this.lock = lock;
    }

    public void getValue() {
        try {
            synchronized (lock) {
                if (MyString.isEmpty()) {
                    lock.wait();
                }

                System.out.println("consumer str = " + MyString.getStr());
                MyString.setStr("");
                lock.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
