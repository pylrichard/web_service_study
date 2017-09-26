package com.bd.java_multithread_core_tech.chapter7.thread_group_stop_threads;

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
