package com.bd.geek.design.pattern.factory.pizza;

public class LondonCheesePizza extends Pizza {
    @Override
    public void prepare() {
        super.setName("LondonCheesePizza");
        System.out.println(name + " preparing;");
    }
}
