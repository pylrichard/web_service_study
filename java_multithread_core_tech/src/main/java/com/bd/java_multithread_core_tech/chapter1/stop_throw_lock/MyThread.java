package com.bd.java_multithread_core_tech.chapter1.stop_throw_lock;

public class MyThread extends Thread {
    private SyncObject obj;

    public MyThread(SyncObject obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        obj.printString("b", "bb");
    }
}
