package com.bd.java_multithread_core_tech.chapter2.sync_block_string_constant;

public class SyncBlockStringConstantTest {
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
