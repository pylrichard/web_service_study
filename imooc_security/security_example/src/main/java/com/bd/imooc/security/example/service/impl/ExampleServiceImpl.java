package com.bd.imooc.security.example.service.impl;

import com.bd.imooc.security.example.service.ExampleService;

public class ExampleServiceImpl implements ExampleService {
    @Override
    public String greeting(String name) {
        System.out.println("greeting");

        return "hello " + name;
    }
}
