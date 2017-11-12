package com.bd.java.multithread.core.tech.chapter3.inheritable_thread_local;

import java.util.Date;

//使用ThreadLocalExt extends ThreadLocal，则父线程main和子线程ta拥有各自的初始值
//子线程可以获取从父线程继承的值
public class InheritableThreadLocalExt extends InheritableThreadLocal {
    @Override
    protected Object initialValue() {
        return new Date().getTime();
    }

    //子线程获取值的同时，父线程修改了InheritableThreadLocal中的值，子线程获取到的值是旧值
    @Override
    protected Object childValue(Object parentValue) {
        return parentValue + " child value";
    }
}
