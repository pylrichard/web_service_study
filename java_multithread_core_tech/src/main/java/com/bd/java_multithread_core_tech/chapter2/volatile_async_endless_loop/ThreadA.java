package com.bd.java_multithread_core_tech.chapter2.volatile_async_endless_loop;

//volatile是线程同步的轻量级实现，性能比synchronized好，volatile只修饰变量，synchronized可以修饰方法和代码块
//多线程访问volatile不会阻塞，synchronized会发生阻塞
//volatile保证数据可见性，不保证原子性
//synchronized保证原子性，间接保证可见性，将公共堆栈和私有堆栈的数据会做同步
//volatile解决变量在多个线程之间的可见性，synchronized解决多个线程之间访问资源的同步性
public class ThreadA extends Thread {
    //volatile强制线程访问isRunning时，从公共堆栈取值，volatile增加对象成员变量在多个线程间的可见性
    //volatile不支持原子性
    //volatile private boolean isRunning = true;

    //isRunning存在于公共堆栈和线程私有堆栈中
    //JVM设置为-server模式(Linux默认启动模式)时为了执行效率，线程在私有堆栈中取得isRunning的值为true
    //setRunning更新的是公有堆栈中的值，原因就是私有堆栈和公共堆栈的值不同步造成的，所以执行结果是死循环
    private boolean isRunning = true;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        System.out.println("run begin");
        while (isRunning == true) {}
        System.out.println("run end");
    }
}
