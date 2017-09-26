package com.bd.java_multithread_core_tech.chapter1.stop_throw_lock;

public class StopThreadTest {
    public static void main(String[] args) {
        try {
            SyncObject obj = new SyncObject();
            MyThread t = new MyThread(obj);
            t.start();
            //睡眠时间要小于SyncObject.printString()中的睡眠时间
            Thread.sleep(500);
            //stop释放锁会造成数据不一致的结果
            t.stop();

            System.out.println(obj.getUserName() + " " + obj.getPassword());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
