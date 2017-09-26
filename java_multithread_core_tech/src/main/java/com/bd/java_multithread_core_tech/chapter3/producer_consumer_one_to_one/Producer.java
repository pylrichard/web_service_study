package com.bd.java_multithread_core_tech.chapter3.producer_consumer_one_to_one;

public class Producer {
    private String lock;

    public Producer(String lock) {
        super();
        this.lock = lock;
    }

    public void setValue() {
        try {
            synchronized (lock) {
                if (!MyString.isEmpty()) {
                    lock.wait();
                }

                String str = System.currentTimeMillis() + "_" + System.nanoTime();
                System.out.println("producer str = " + str);
                MyString.setStr(str);
                lock.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
