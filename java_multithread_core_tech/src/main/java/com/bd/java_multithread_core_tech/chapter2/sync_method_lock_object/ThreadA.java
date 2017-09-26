package com.bd.java_multithread_core_tech.chapter2.sync_method_lock_object;

public class ThreadA extends Thread {
    private MyObject obj;

    public ThreadA(MyObject obj) {
        super();
        this.obj = obj;
    }

    @Override
    public void run() {
        super.run();
        //methodA添加synchronized关键字时，访问MyObject对象的同步方法，持有对象锁
        obj.methodA();
    }
}
