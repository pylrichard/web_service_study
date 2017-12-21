package com.bd.imooc.spring.aop.api;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MoocBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("MoocBeforeAdvice : " + method.getName() + "     " +
                target.getClass().getName());
    }
}
