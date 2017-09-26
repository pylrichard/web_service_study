package com.bd.java_multithread_core_tech.chapter2.two_object_two_lock;

public class HasSelfPrivateNum {
    // 实例变量存在非线程安全问题
    private int num = 0;

    // 同步方法则是线程安全的
    synchronized  public void addNum(String userName) {
        try {
            if(userName.equals("a")) {
                num = 100;
                System.out.println("a set over");
                Thread.sleep(200);
            } else {
                num = 200;
                System.out.println("b set over");
            }

            System.out.println(userName + " num= " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
