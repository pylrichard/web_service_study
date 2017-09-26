package com.bd.java_multithread_core_tech.chapter7.thread_group_exception_nostop;

import com.bd.java_multithread_core_tech.chapter7.thread_group_exception_all_stop.MyThread;

public class ThreadGroupExceptionNoStopTest {
    private static int THREAD_NUM = 3;

    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("group");
        MyThread[] threads = new MyThread[THREAD_NUM];

        for (int i = 0; i < THREAD_NUM; i++) {
            threads[i] = new MyThread(group, "t" + (i + 1), "1");
            threads[i].start();
        }

        //解析a为数字触发异常，不会影响其它线程运行
        MyThread t = new MyThread(group, "error", "a");
        t.start();
    }
}
