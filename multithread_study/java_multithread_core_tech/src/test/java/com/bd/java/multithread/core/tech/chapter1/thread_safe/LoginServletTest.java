package com.bd.java.multithread.core.tech.chapter1.thread_safe;

public class LoginServletTest {
    public static void main(String[] args) {
        ALogin a = new ALogin();
        a.start();

        BLogin b = new BLogin();
        b.start();
    }
}
