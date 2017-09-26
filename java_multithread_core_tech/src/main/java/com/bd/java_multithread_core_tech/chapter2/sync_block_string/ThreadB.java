package com.bd.java_multithread_core_tech.chapter2.sync_block_string;

public class ThreadB extends Thread {
    private Service service;

    public ThreadB(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        //同步代码块锁定的是不同的局部变量，执行结果是异步
        //service.setValue("B", "BB");
        //调用同步方法，获得this对象锁，执行结果是异步
        service.printValue();
    }
}
