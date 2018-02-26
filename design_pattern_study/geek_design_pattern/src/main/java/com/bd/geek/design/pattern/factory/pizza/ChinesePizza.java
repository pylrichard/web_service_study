package com.bd.geek.design.pattern.factory.pizza;

public class ChinesePizza extends Pizza {
    @Override
    public void prepare() {
        super.setName("ChinesePizza");
        System.out.println(name + " preparing;");
    }
}
