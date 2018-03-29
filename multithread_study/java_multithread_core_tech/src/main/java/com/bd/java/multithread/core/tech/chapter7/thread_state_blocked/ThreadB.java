package com.bd.java.multithread.core.tech.chapter7.thread_state_blocked;

public class ThreadB extends Thread {
    @Override
    public void run() {
        MyService.method();
    }
}
