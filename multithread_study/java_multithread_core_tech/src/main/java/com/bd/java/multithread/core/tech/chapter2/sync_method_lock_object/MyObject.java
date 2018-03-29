package com.bd.java.multithread.core.tech.chapter2.sync_method_lock_object;

public class MyObject {
    //只有共享资源的读写访问需要同步
    //观察添加synchronized关键字前后的输出结果
    synchronized public void methodA() {
        try {
            System.out.println("begin methodA thread name = " + Thread.currentThread().getName());
            Thread.sleep(2000);
            System.out.println("end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //观察添加synchronized关键字前后的输出结果
    synchronized public void methodB() {
        try {
            System.out.println("begin methodB thread name = " + Thread.currentThread().getName());
            Thread.sleep(2000);
            System.out.println("end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
