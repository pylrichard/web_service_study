package com.bd.java_multithread_core_tech.chapter2.thread3;

public class PublicVar {
    private String userName = "A";
    private String password = "AA";

    synchronized public void setValue(String userName, String password) {
        try {
            this.userName = userName;
            //设置userName后进行睡眠，睡眠时间大于main线程睡眠时间
            Thread.sleep(2000);
            this.password = password;
            System.out.println("setValue thread name = " + Thread.currentThread().getName()
                                + " userName = " + userName + " password = " + password);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void getValue() {
        System.out.println("getValue thread name = " + Thread.currentThread().getName()
                            + " userName = " + userName + " password = " + password);
    }
}
