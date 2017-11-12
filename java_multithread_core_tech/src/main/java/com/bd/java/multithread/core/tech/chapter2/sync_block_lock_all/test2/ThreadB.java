package com.bd.java.multithread.core.tech.chapter2.sync_block_lock_all.test2;

public class ThreadB extends Thread {
    private Service service;
    private MyObject object;

    public ThreadB(Service service, MyObject object) {
        super();
        this.service = service;
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        //验证当其它线程执行x对象的synchronized方法时执行结果是同步的
        //object.method1();
        //验证当其它线程执行x对象方法的同步代码块时执行结果是同步的
        object.method2();
    }
}
