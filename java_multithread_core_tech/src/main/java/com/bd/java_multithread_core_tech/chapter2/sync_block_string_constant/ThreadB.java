package com.bd.java_multithread_core_tech.chapter2.sync_block_string_constant;

public class ThreadB extends Thread {
    private Service service;

    public ThreadB(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        //和A线程传入同一个String常量
        service.method("AA");
    }
}
