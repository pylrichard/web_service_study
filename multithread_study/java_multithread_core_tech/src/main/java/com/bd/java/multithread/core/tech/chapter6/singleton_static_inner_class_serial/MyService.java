package com.bd.java.multithread.core.tech.chapter6.singleton_static_inner_class_serial;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class MyService implements Serializable {
    private static final long serialVersionUID = 888L;

    private static class MyServiceHandler {
        private static final MyService service = new MyService();

        public static MyService getService() {
            return service;
        }
    }

    public static MyService getInstance() {
        return MyServiceHandler.getService();
    }

    /**
     * 在反序列化中调用readResolve()，得到与序列化中相同的实例
     */
    protected Object readResolve() throws ObjectStreamException {
        System.out.println("readResolve");
        return MyServiceHandler.getService();
    }
}
