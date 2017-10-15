package com.imooc.aop.pattern;

public class RealSubject implements Subject {
    public void request() {
        System.out.println("real subject exec request");
    }
}
