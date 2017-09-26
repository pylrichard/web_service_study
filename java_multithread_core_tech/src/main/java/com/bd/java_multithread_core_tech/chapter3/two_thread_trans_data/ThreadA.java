package com.bd.java_multithread_core_tech.chapter3.two_thread_trans_data;

public class ThreadA extends Thread {
    private Service service;

    public ThreadA(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                service.add();
                System.out.println(Thread.currentThread().getName() + " add " + (i + 1) + " element");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
