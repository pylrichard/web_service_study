package com.bd.java.multithread.core.tech.chapter2.volatile_sync_endless_loop;

import com.bd.java.multithread.core.tech.chapter2.volatile_sync_endless_loop.Task;

//运行在-server服务器模式64bit的JVM上，会出现死循环
//解决办法是使用volatile关键字，作用是强制从公共堆栈中取变量值，而不是从线程私有堆栈中取值
public class VolatileSyncEndlessLoopTest {
    public static void main(String[] args) {
        Task task = new Task();
        //通过子线程执行method，在子线程休眠时，main线程可以执行setRunning
        Thread t = new Thread(task);
        t.start();
        //死循环，不会执行setRunning
        //service.method();
        System.out.println(Thread.currentThread().getName() + " stop ThreadA");
        task.setRunning(false);
    }
}
