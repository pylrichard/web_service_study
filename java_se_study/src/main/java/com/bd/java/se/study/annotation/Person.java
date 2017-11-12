package com.bd.java.se.study.annotation;

/**
 * 接口注解不能继承
 */
@Description(desc = "Person")
public interface Person {
    public void say();
    public void walk();
}
