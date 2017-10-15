package com.imooc.aop.proxy.static_proxy;

public class Proxy implements Subject {
    private RealSubject realSubject;

    public Proxy(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    public void request() {
        System.out.println("before");
        try {
            realSubject.request();
        } catch (Exception e) {
            System.out.println("ex: " + e.getMessage());
            throw e;
        } finally {
            System.out.println("after");
        }
    }

    /**
     * 静态代理在类增加方法时，需要同步增加调用，而动态代理不需要
     */
    public void test() {
        realSubject.test();
    }
}
