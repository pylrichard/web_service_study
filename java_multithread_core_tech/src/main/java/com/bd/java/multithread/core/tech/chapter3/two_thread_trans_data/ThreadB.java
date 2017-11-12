package com.bd.java.multithread.core.tech.chapter3.two_thread_trans_data;

public class ThreadB extends Thread {
    private Service service;

    public ThreadB(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        try {
            //通过轮询检测实现线程间通信
            //轮询时间间隔小，浪费CPU资源
            //轮询时间间隔大，可能得到不想得到的数据
            while (true) {
                int size = service.getSize();
                //System.out.println("size = " + size);

                if (size == 5) {
                    System.out.println(Thread.currentThread().getName() + " exit");
                    throw new InterruptedException();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
