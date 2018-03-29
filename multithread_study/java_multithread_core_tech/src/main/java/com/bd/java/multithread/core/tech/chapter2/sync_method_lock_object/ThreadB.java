package com.bd.java.multithread.core.tech.chapter2.sync_method_lock_object;

public class ThreadB extends Thread {
    private MyObject obj;

    public ThreadB(MyObject obj) {
        super();
        this.obj = obj;
    }

    @Override
    public void run() {
        super.run();
        //methodA没有添加synchronized关键字时，与ThreadA访问同一个非同步方法methodA，观察输出结果
        //obj.methodA();
        //methodB没有添加synchronized关键字时，ThreadB在ThreadA持有MyObject对象锁时可以异步调用非同步方法methodB
        //methodB添加synchronized关键字时，ThreadB在ThreadA持有MyObject对象锁时要进行等待，既是同步调用
        obj.methodB();
    }
}
