package com.bd.java.multithread.core.tech.chapter6.singleton.double_check.lock;

public class MyService {
    volatile private static MyService service;

    /**
     * 使用DCL检查机制，保证不需要同步的代码的异步执行性，又保证单例模式
     */
    public static MyService getInstance() {
        try {
            if (service == null) {
                Thread.sleep(2000);
                synchronized (MyService.class) {
                    if (service == null) {
                        //延迟加载，使用时才创建对象
                        service = new MyService();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return service;
    }
}
