package com.bd.imooc.study.aop.domain;

import com.bd.imooc.study.aop.annotation.NeedSecured;

@NeedSecured
public class Product {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
