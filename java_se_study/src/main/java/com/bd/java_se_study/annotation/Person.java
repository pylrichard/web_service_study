package com.bd.java_se_study.annotation;

/**
 * 接口注解不能继承
 */
@Description(desc = "Person")
public interface Person {
    public void say();
    public void walk();
}
