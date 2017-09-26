package com.bd.java_multithread_core_tech.chapter2.double_sync_block;

public class Service {
    public void methodA() {
        try {
            //一个线程访问对象的一个同步代码块时，其它线程访问同一个对象的其它同步代码块会阻塞
            //synchronized使用的对象监视器是一个
            synchronized (this) {
                System.out.println("methodA begin");
                Thread.sleep(2000);
                System.out.println("methodA end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void methodB() {
        synchronized (this) {
            System.out.println("methodB begin");
            System.out.println("methodB end");
        }
    }
}
