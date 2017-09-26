package com.bd.java_multithread_core_tech.chapter7.thread_state_blocked;

public class ThreadA extends Thread {
    @Override
    public void run() {
        MyService.method();
    }
}
