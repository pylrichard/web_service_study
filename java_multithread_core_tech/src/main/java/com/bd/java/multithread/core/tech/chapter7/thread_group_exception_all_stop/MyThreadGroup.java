package com.bd.java.multithread.core.tech.chapter7.thread_group_exception_all_stop;

public class MyThreadGroup extends ThreadGroup {
    public MyThreadGroup(String name) {
        super(name);
    }

    /**
     * 异常处理中执行interrupt()，停止所有线程
     * 注意每个线程对象的run()不要有异常catch语句，否则uncaughtException()不会执行
     *
     * @param t 出现异常的线程对象
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        super.uncaughtException(t, e);
        this.interrupt();
    }
}
