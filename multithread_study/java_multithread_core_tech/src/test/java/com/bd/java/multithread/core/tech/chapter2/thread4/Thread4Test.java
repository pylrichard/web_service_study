package com.bd.java.multithread.core.tech.chapter2.thread4;

public class Thread4Test {
    public static void main(String[] args) {
        Task task = new Task();
        ThreadA t1 = new ThreadA(task);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(task);
        t2.setName("t2");
        t2.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long beginTime = CommonUtils.beginTime1;
        if (CommonUtils.beginTime2 < CommonUtils.beginTime1) {
            beginTime = CommonUtils.beginTime2;
        }
        long endTime = CommonUtils.endTime1;
        if (CommonUtils.endTime2 > CommonUtils.endTime1) {
            endTime = CommonUtils.endTime2;
        }
        System.out.println("execution time: " + (endTime - beginTime) / 1000 + "s");
    }
}
