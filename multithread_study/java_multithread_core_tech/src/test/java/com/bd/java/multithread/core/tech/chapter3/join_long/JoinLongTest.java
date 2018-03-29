package com.bd.java.multithread.core.tech.chapter3.join_long;

public class JoinLongTest {
    public static void main(String[] args) {
        try {
            MyThread t = new MyThread();
            t.start();
            //join(long)设定等待时间，超时则不等待
            //join内部使用wait(long)实现，会释放锁
            t.join(2000);
            //sleep()不会释放锁
            //Thread.sleep(2000);
            System.out.println("end time " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
