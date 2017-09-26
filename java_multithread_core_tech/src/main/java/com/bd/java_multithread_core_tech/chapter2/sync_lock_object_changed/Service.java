package com.bd.java_multithread_core_tech.chapter2.sync_lock_object_changed;

public class Service {
    private String lock = "123";

    public void method() {
        String name = Thread.currentThread().getName();

        try {
            synchronized (lock) {
                System.out.println(name + " begin");
                lock = "456";
                Thread.sleep(2000);
                System.out.println(name + " end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
