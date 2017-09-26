package com.bd.java_multithread_core_tech.chapter2.sync_not_extends;

public class Sub extends Main {
    @Override
    synchronized public void method() {
        try {
            System.out.println("thread name " + Thread.currentThread().getName() + " sub method begin");
            Thread.sleep(100);
            //同步不能继承，需要添加synchronized关键字
            super.method();
            System.out.println("thread name " + Thread.currentThread().getName() + " sub method end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
