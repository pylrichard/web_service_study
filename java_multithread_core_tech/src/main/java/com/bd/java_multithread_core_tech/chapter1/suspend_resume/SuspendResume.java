package com.bd.java_multithread_core_tech.chapter1.suspend_resume;

public class SuspendResume extends Thread {
    private long i = 0;

    public long getI() {
        return i;
    }

    public void setI(long i) {
        this.i = i;
    }

    @Override
    public void run() {
        while(true) {
            i++;
        }
    }
}
