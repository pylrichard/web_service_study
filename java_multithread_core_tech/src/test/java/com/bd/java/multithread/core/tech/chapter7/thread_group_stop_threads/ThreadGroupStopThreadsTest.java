package com.bd.java.multithread.core.tech.chapter7.thread_group_stop_threads;

import com.bd.java.multithread.core.tech.chapter7.thread_group_stop_threads.MyThread;

public class ThreadGroupStopThreadsTest {
    public static void main(String[] args) {
        try {
            ThreadGroup group = new ThreadGroup("group");
            for (int i = 0; i < 5; i++) {
                MyThread t = new MyThread(group, "t" + (i + 1));
                t.start();
            }
            Thread.sleep(1000);
            //将线程组中所有正在运行的线程停止
            group.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
