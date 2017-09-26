package com.bd.java_multithread_core_tech.chapter2.sync_one_list;

public class ThreadA extends Thread {
    private OneList list;

    public ThreadA(OneList list) {
        super();
        this.list = list;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.add(list, "A");
    }
}
