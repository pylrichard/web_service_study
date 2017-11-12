package com.bd.java.multithread.core.tech.chapter1.thread_safe;

public class ALogin extends Thread {
    @Override
    public void run() {
        LoginServlet.doPost("a", "aa");
    }
}
