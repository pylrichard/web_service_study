package com.bd.imooc.spring.primer.ioc.interfaces;

public class OneInterfaceImpl implements OneInterface {
    @Override
    public void say(String arg) {
        System.out.println("ServiceImpl say: " + arg);
    }
}
