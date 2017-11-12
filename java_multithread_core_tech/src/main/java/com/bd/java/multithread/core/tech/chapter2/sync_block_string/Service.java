package com.bd.java.multithread.core.tech.chapter2.sync_block_string;

public class Service {
    private String userName;
    private String password;
    //Java支持任意对象(对象成员变量)作为对象监视器
    //t1和t2虽然不是锁定this对象，但此处锁定的是同一个非this对象，同步执行
    //private String lockObject = new String();

    public void setValue(String userName, String password) {
        try {
            //修改lockObject为局部变量，synchronized的对象监视器是不同对象，t1和t2之间是异步执行
            //要实现同步，对象监视器必须是同一个对象
            String lockObject = new String();
            //使用同步代码块锁定非this对象，与锁定this对象的同步方法/代码块之间是异步执行的，不会争抢this对象锁
            //同步代码块放在非synchronized方法中声明，不能保证调用方法的线程的执行同步/顺序性
            //虽然同步代码块中的执行顺序是同步的，但也可能出现脏读问题
            synchronized (lockObject) {
                System.out.println("thread name : " + Thread.currentThread().getName() + " begin");
                this.userName = userName;
                Thread.sleep(1000);
                this.password = password;
                System.out.println("thread name : " + Thread.currentThread().getName() + " end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void printValue() {
        System.out.println("t2 begin");
        System.out.println("t2 end");
    }
}
