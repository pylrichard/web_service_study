package com.bd.java.multithread.core.tech.chapter1;

public class CurrentThreadExt extends Thread {
    public CurrentThreadExt() {
        System.out.println("CurrentThreadExt");
        //调用构造函数的是main线程
        System.out.println("Thread.currentThread().getName()=" + Thread.currentThread().getName());
        System.out.println("Thread.currentThread().isAlive()=" + Thread.currentThread().isAlive());
        //还没有启动CurrentThreadExt子线程
        //Thread-0见Thread.java
        //public Thread() {
        //  "Thread-" + nextThreadNum()赋值给this.name
        //  init(null, null, "Thread-" + nextThreadNum(), 0);
        //}
        //private static int threadInitNumber;
        //private static synchronized int nextThreadNum() {
        //    return threadInitNumber++;
        //}
        //调用的是父类的getName，返回Thread-0
        System.out.println("this.getName()=" + this.getName());
        System.out.println("this.isAlive()=" + this.isAlive());
        //this代表CurrentThreadExt对象实例，和main线程不是一个线程
        System.out.println("Thread.currentThread() == this " + (Thread.currentThread() == this));
    }

    @Override
    public void run() {
        System.out.println("run");
        System.out.println("Thread.currentThread().getName()=" + Thread.currentThread().getName());
        System.out.println("Thread.currentThread().isAlive()=" + Thread.currentThread().isAlive());
        //public Thread(Runnable target) {
        //  init(null, target, "Thread-" + nextThreadNum(), 0);
        //}
        //private void init(...) {
        //  this.target = target;
        //}
        //将Thread对象绑定到pravite变量target上
        //在t2被执行时即t2.run()被调用时，会调用target.run()
        //在run()被执行时，this.getName()返回的是target.getName()，Thread.currentThread().getName()返回的是t2.getName()
        System.out.println("this.getName()=" + this.getName());
        //不是通过CurrentThreadExt对象调用start()，此处都是false
        System.out.println("this.isAlive()=" + this.isAlive());
        System.out.println("Thread.currentThread() == this " + (Thread.currentThread() == this));
    }
}
