package com.bd.java_multithread_core_tech.chapter6.singleton_static_code_block;

public class MyService {
    private static MyService service = null;

    static {
        service = new MyService();
    }

    public static MyService getInstance() {
        return service;
    }
}
