package com.bd.java_multithread_core_tech.chapter1.suspend_resume;

public class NoSameValue {
    private String userName = "a";
    private String password = "aa";

    public void setValue(String userName, String password) {
        this.userName = userName;

        if(Thread.currentThread().getName().equals("t1")) {
            System.out.println("t1 suspend");
            Thread.currentThread().suspend();
        }

        this.password = password;
    }

    public void print() {
        System.out.println(userName + " " + password);
    }
}
