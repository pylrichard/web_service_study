package com.bd.java_multithread_core_tech.chapter2.sync_lock_properties_changed;

public class SyncLockPropertiesChangedTest {
    public static void main(String[] args) {
        try {
            Service service = new Service();
            UserInfo info = new UserInfo();

            ThreadA t1 = new ThreadA(service, info);
            t1.setName("t1");
            t1.start();
            Thread.sleep(50);
            ThreadB t2 = new ThreadB(service, info);
            t2.setName("t2");
            t2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
