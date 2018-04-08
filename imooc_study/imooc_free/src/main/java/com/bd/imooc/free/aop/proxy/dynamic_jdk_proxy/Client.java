package com.bd.imooc.free.aop.proxy.dynamic_jdk_proxy;

import com.bd.imooc.free.aop.proxy.static_proxy.RealSubject;
import com.bd.imooc.free.aop.proxy.static_proxy.Subject;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        /*
            生成代理类源文件$ProxyX.class
            其中super.h.invoke(this, mX, (Object[])null);调用的是JDKProxySubject.invoke()
         */
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Subject subject = (Subject) Proxy.newProxyInstance(Client.class.getClassLoader(),
                                                            new Class[]{Subject.class},
                                                            new JDKProxySubject(new RealSubject()));
        subject.request();
        //不需要在JDKProxySubject增加相应调用
        subject.test();
    }
}
