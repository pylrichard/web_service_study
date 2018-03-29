package com.bd.java.multithread.core.tech.chapter7.thread_group_more_level;

public class ThreadGroupMoreLevelTest {
    public static void main(String[] args) {
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        //添加子线程组
        ThreadGroup subGroup = new ThreadGroup(mainGroup, "sub");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //线程在运行状态才能被线程组管理
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(subGroup, runnable);
        t.setName("t");
        //线程启动后才会归到线程组subGroup中
        t.start();

        ThreadGroup[] groups = new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
        //拷贝子线程组信息
        Thread.currentThread().getThreadGroup().enumerate(groups);
        System.out.println("main thread has sub group num: " + groups.length + " name: " + groups[0].getName());
        Thread[] threads = new Thread[groups[0].activeCount()];
        groups[0].enumerate(threads);
        System.out.println(threads[0].getName());
    }
}
