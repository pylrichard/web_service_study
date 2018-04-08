package com.bd.imooc.free.aop.proxy.dynamic_cglib_proxy;

import com.bd.imooc.free.aop.proxy.static_proxy.RealSubject;
import com.bd.imooc.free.aop.proxy.static_proxy.Subject;
import net.sf.cglib.proxy.Enhancer;

public class Client {
    public static void main(String[] args){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealSubject.class);
        enhancer.setCallback(new CGLibMethodInterceptor());

        //生成代理类
        Subject subject = (Subject) enhancer.create();
        subject.test();
    }
}
