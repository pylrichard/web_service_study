package com.bd.java.multithread.core.tech.chapter6.singleton.static_code.block;

public class MyService {
    private static MyService service = null;

    static {
        service = new MyService();
    }

    public static MyService getInstance() {
        return service;
    }
}
