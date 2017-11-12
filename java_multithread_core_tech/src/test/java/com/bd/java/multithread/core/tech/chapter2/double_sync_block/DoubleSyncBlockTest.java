package com.bd.java.multithread.core.tech.chapter2.double_sync_block;

import com.bd.java.multithread.core.tech.chapter2.double_sync_block.Service;
import com.bd.java.multithread.core.tech.chapter2.double_sync_block.ThreadA;
import com.bd.java.multithread.core.tech.chapter2.double_sync_block.ThreadB;

public class DoubleSyncBlockTest {
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
