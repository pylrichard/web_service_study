package com.bd.java.multithread.core.tech.chapter3.wait_condition;

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
