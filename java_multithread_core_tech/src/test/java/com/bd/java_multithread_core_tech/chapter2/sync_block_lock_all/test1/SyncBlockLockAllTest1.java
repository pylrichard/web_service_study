package com.bd.java_multithread_core_tech.chapter2.sync_block_lock_all.test1;

//验证多个线程同时执行synchronized(x) {}同步代码块时执行结果是同步的
public class SyncBlockLockAllTest1 {
    public static void main(String[] args) {
        Service service = new Service();
        //同步同一个object对象，执行结果是同步的
        //MyObject object = new MyObject();

        //同步不同的object对象，执行结果是异步的
        MyObject object1 = new MyObject();
        MyObject object2 = new MyObject();

        //ThreadA t1 = new ThreadA(service, object);
        ThreadA t1 = new ThreadA(service, object1);
        t1.setName("t1");
        t1.start();
        //ThreadB t2 = new ThreadB(service, object);
        ThreadB t2 = new ThreadB(service, object2);
        t2.setName("t2");
        t2.start();
    }
}
