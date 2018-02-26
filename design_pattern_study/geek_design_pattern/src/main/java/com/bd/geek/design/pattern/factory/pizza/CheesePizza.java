package com.bd.geek.design.pattern.factory.pizza;

public class CheesePizza extends Pizza {
    @Override
    public void prepare() {
        super.setName("CheesePizza");
        System.out.println(name + " preparing;");
    }
}
