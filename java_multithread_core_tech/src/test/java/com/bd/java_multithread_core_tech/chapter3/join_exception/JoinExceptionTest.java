package com.bd.java_multithread_core_tech.chapter3.join_exception;

public class JoinExceptionTest {
    public static void main(String[] args) {
        try {
            ThreadB tb = new ThreadB();
            tb.start();
            Thread.sleep(10);
            ThreadC tc = new ThreadC(tb);
            tc.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
