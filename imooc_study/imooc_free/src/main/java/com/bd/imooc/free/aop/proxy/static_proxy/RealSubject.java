package com.bd.imooc.free.aop.proxy.static_proxy;

public class RealSubject implements Subject {
    public void request() {
        System.out.println("real subject exec request");
    }

    @Override
    public void test() {
        System.out.println("real subject exec test");
    }
}
