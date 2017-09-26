package com.bd.java_multithread_core_tech.chapter7.thread_group_exception_all_stop;

public class MyThread extends Thread {
    private String numStr;

    public MyThread(ThreadGroup group, String name, String numStr) {
        super(group, name);
        this.numStr = numStr;
    }

    @Override
    public void run() {
        int num = Integer.parseInt(numStr);

        try {
            while (!this.isInterrupted()) {
                System.out.println("dead loop " + Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
