package com.bd.java_multithread_core_tech.chapter1.thread_safe;

public class ALogin extends Thread {
    @Override
    public void run() {
        LoginServlet.doPost("a", "aa");
    }
}
