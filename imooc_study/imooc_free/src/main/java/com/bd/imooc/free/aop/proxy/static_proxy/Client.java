package com.bd.imooc.free.aop.proxy.static_proxy;

public class Client {
    public static void main(String[] args){
        Subject subject = new Proxy(new RealSubject());
        subject.request();
    }
}
