package com.bd.java.multithread.core.tech.chapter2.sync_block_object;

import com.bd.java.multithread.core.tech.chapter2.sync_block_object.Service;
import com.bd.java.multithread.core.tech.chapter2.sync_block_object.ThreadA;
import com.bd.java.multithread.core.tech.chapter2.sync_block_object.ThreadB;

public class SyncBlockObjectTest {
    public static void main(String[] args) {
        Service service = new Service();
        ThreadA t1 = new ThreadA(service);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(service);
        t2.setName("t2");
        t2.start();
    }
}
