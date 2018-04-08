package com.bd.imooc.spring.primer.bean.annotation.java.based;

public class MyDriverManager {
    public MyDriverManager(String url, String userName, String password) {
        System.out.println("url : " + url);
        System.out.println("userName: " + userName);
        System.out.println("password: " + password);
    }
}
