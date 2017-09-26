package com.bd.java_multithread_core_tech.chapter3.wait_condition;

public class WaitConditionTest {
    public static void main(String[] args) throws InterruptedException {
        String lock = new String("");
        AddService addService = new AddService(lock);
        SubService subService = new SubService(lock);
        ThreadSub t1 = new ThreadSub(subService);
        t1.setName("t1");
        t1.start();
        ThreadSub t2 = new ThreadSub(subService);
        t2.setName("t2");
        t2.start();
        Thread.sleep(1000);
        ThreadAdd t3 = new ThreadAdd(addService);
        t3.setName("t3");
        t3.start();
    }
}
