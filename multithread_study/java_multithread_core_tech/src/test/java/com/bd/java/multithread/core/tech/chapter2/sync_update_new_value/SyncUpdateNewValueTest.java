package com.bd.java.multithread.core.tech.chapter2.sync_update_new_value;

public class SyncUpdateNewValueTest {
    public static void main(String[] args) {
        try {
            Service service = new Service();
            ThreadA t1 = new ThreadA(service);
            t1.start();
            Thread.sleep(1000);
            ThreadB t2 = new ThreadB(service);
            t2.start();
            System.out.println("send stop cmd");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
