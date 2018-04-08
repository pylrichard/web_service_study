package com.bd.imooc.spring.primer.bean.annotation.java.based;

public class StringStore implements Store<String> {
    public void init() {
        System.out.println("This is init.");
    }

    public void destroy() {
        System.out.println("This is destroy.");
    }
}
