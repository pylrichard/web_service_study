package com.bd.java.multithread.core.tech.chapter2.sync_two_object_static_method;

import com.bd.java.multithread.core.tech.chapter2.sync_two_object_static_method.Service;
import com.bd.java.multithread.core.tech.chapter2.sync_two_object_static_method.ThreadA;
import com.bd.java.multithread.core.tech.chapter2.sync_two_object_static_method.ThreadB;

public class SyncTwoObjectStaticMethodTest {
    public static void main(String[] args) {
        //调用相同Class不同对象的同步静态方法，持有Class锁，Class锁对所有类对象起作用
        //执行结果是同步的
        Service service1 = new Service();
        Service service2 = new Service();

        ThreadA t1 = new ThreadA(service1);
        t1.setName("t1");
        t1.start();
        ThreadB t2 = new ThreadB(service2);
        t2.setName("t2");
        t2.start();
    }
}
