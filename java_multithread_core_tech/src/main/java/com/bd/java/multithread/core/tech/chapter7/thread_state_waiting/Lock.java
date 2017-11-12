package com.bd.java.multithread.core.tech.chapter7.thread_state_waiting;

public class Lock {
    private static final Byte lock = new Byte("0");

    public static Byte getLock() {
        return lock;
    }
}
