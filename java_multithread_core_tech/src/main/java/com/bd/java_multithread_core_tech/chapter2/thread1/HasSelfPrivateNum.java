package com.bd.java_multithread_core_tech.chapter2.thread1;

public class HasSelfPrivateNum {
    public void addNum(String userName) {
        try {
            // 方法中的变量(私有)不存在非线程安全问题
            int num = 0;

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
