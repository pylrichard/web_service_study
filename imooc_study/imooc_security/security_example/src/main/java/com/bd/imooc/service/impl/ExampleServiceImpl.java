package com.bd.imooc.service.impl;

import com.bd.imooc.service.ExampleService;

public class ExampleServiceImpl implements ExampleService {
    @Override
    public String greeting(String name) {
        System.out.println("greeting");

        return "hello " + name;
    }
}
