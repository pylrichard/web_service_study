package com.bd.geek.design.pattern.factory.pizza;

public class NewYorkCheesePizza extends Pizza {
    @Override
    public void prepare() {
        super.setName("NewYorkCheesePizza");
        System.out.println(name + " preparing;");
    }
}
