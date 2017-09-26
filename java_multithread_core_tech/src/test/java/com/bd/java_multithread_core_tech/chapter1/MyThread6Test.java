package com.bd.java_multithread_core_tech.chapter1;

public class MyThread6Test {
    public static void main(String[] args) {
        MyThread6 t = new MyThread6();
        t.start();
        //run()被Thread-0自动调用
        //t.run();
    }
}
