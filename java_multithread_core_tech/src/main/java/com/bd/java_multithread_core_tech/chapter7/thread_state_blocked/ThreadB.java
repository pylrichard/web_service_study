package com.bd.java_multithread_core_tech.chapter7.thread_state_blocked;

public class ThreadB extends Thread {
    @Override
    public void run() {
        MyService.method();
    }
}
