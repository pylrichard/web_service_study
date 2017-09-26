package com.bd.java_multithread_core_tech.chapter7.thread_group_exception_all_stop;

public class ThreadGroupExceptionAllStopTest {
    private static int THREAD_NUM = 3;

    public static void main(String[] args) {
        MyThreadGroup group = new MyThreadGroup("group");
        MyThread[] threads = new MyThread[THREAD_NUM];

        for (int i = 0; i < THREAD_NUM; i++) {
            threads[i] = new MyThread(group, "t" + (i + 1), "1");
            threads[i].start();
        }

        //解析a为数字触发异常，其它线程也停止运行
        MyThread t = new MyThread(group, "error", "a");
        t.start();
    }
}
