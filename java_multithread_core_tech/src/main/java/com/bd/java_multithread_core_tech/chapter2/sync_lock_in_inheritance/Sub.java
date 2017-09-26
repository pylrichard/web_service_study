package com.bd.java_multithread_core_tech.chapter2.sync_lock_in_inheritance;

public class Sub extends Main {
    synchronized public void subMethod() {
        try {
            while (i > 0) {
                i--;
                System.out.println("subMethod i = " + i);
                Thread.sleep(100);
                //子类可以通过可重入锁调用父类的同步方法
                this.mainMethod();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
