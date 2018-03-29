package com.bd.java.multithread.core.tech.chapter1.suspend_resume;

public class SyncObjectTest {
    public static void main(String[] args) {
        try {
            final SyncObject obj = new SyncObject();
            Thread t1 = new Thread() {
                @Override
                public void run() {
                    obj.printString();
                }
            };
            t1.setName("t1");
            t1.start();
            Thread.sleep(1000);

            Thread t2 = new Thread() {
                @Override
                public void run() {
                    //t2启动后进入不了printString()，printString()被t1锁定并suspend
                    obj.printString();
                }
            };
            t2.setName("t2");
            t2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
