package com.bd.java_multithread_core_tech.chapter6.singleton_static_inner_class;

/**
 * 静态内部类解决单例模式多线程安全问题
 */
public class MyService {
    private static class MyServiceHandler {
        private static MyService service = new MyService();

        public static MyService getService() {
            return service;
        }
    }

    public static MyService getInstance() {
        return MyServiceHandler.getService();
    }
}
