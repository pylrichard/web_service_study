package com.bd.imooc.spring.bean.scope;

public class BeanScope {
    /**
     * 见spring-bean-scope.xml
     */
    public void say() {
        System.out.println("BeanScope say : " + this.hashCode());
    }
}
