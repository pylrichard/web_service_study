package com.bd.java_multithread_core_tech.chapter3.wait_condition;

public class AddService {
    private String lock;

    public AddService(String lock) {
        super();
        this.lock = lock;
    }

    public void add() {
        synchronized (lock) {
            MyList.add("pyl");
            lock.notifyAll();
        }
    }
}
