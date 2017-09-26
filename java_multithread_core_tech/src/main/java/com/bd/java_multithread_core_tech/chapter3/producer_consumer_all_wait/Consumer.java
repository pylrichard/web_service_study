package com.bd.java_multithread_core_tech.chapter3.producer_consumer_all_wait;

public class Consumer {
    private String lock;

    public Consumer(String lock) {
        super();
        this.lock = lock;
    }

    public void getValue() {
        try {
            synchronized (lock) {
                String name = Thread.currentThread().getName();
                while (MyString.isEmpty()) {
                    System.out.println("consumer " + name +" waiting");
                    lock.wait();
                }

                System.out.println("consumer " + name +" running");
                MyString.setStr("");
                //唤醒是随机的，所以可能唤醒同类线程，比如生产者唤醒生产者，消费者唤醒消费者
                lock.notify();
                //避免假死，唤醒所有等待线程
                //lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
