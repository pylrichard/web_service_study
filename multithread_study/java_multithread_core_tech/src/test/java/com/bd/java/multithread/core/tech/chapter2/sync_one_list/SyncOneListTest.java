package com.bd.java.multithread.core.tech.chapter2.sync_one_list;

public class SyncOneListTest {
    public static void main(String[] args) throws InterruptedException {
        OneList list = new OneList();
        ThreadA t1 = new ThreadA(list);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(list);
        t2.setName("t2");
        t2.start();

        Thread.sleep(3000);
        //出现脏读，结果为2，因为A和B两个线程以异步方式返回list大小
        System.out.println("list size = " + list.getSize());
    }
}
