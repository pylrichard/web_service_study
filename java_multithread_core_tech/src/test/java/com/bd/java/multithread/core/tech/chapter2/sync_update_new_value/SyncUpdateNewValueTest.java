package com.bd.java.multithread.core.tech.chapter2.sync_update_new_value;

import com.bd.java.multithread.core.tech.chapter2.sync_update_new_value.Service;
import com.bd.java.multithread.core.tech.chapter2.sync_update_new_value.ThreadA;
import com.bd.java.multithread.core.tech.chapter2.sync_update_new_value.ThreadB;

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
