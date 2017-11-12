package com.bd.java.multithread.core.tech.chapter2.sync_one_list;

public class ThreadB extends Thread {
    private OneList list;

    public ThreadB(OneList list) {
        super();
        this.list = list;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.add(list, "B");
    }
}
