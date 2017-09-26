package com.bd.java_multithread_core_tech.chapter2.sync_lock_properties_changed;

public class Service {
    public void method(UserInfo info) {
        synchronized (info) {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(name + " begin");
                //只要锁定的对象不变，即使对象属性改变，执行结果还是同步的
                info.setName("pyl");
                Thread.sleep(1000);
                System.out.println(name + " end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
