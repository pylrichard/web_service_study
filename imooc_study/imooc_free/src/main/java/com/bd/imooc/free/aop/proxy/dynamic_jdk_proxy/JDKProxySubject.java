package com.bd.imooc.free.aop.proxy.dynamic_jdk_proxy;

import com.bd.imooc.free.aop.proxy.static_proxy.RealSubject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JDKProxySubject implements InvocationHandler {
    private RealSubject realSubject;

    public JDKProxySubject(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("before");
        Object result = null;

        try {
            //通过反射调用目标方法
            result = method.invoke(realSubject, objects);
        } catch (Exception e) {
            System.out.println("ex: " + e.getMessage());
            throw e;
        } finally {
            System.out.println("after");
        }

        return null;
    }
}
