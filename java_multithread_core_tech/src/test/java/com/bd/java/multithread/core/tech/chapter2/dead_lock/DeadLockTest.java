package com.bd.java.multithread.core.tech.chapter2.dead_lock;

import com.bd.java.multithread.core.tech.chapter2.dead_lock.DeadLockTask;

//jps找到进程id
//jstack -l 进程id查看死锁情况
public class DeadLockTest {
    public static void main(String[] args) {
        try {
            DeadLockTask task = new DeadLockTask();
            task.setName("t1");
            Thread t1 = new Thread(task);
            t1.start();
            Thread.sleep(1000);
            task.setName("t2");
            Thread t2 = new Thread(task);
            t2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
