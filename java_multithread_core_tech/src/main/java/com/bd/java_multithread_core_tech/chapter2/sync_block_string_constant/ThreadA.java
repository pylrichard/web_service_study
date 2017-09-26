package com.bd.java_multithread_core_tech.chapter2.sync_block_string_constant;

public class ThreadA extends Thread {
    private Service service;

    public ThreadA(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        //和B线程传入同一个String常量
        service.method("AA");
    }
}
