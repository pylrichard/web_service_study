package com.bd.geek.design.pattern.factory.pizza;

public class GreekPizza extends Pizza {
    @Override
    public void prepare() {
        super.setName("GreekPizza");
        System.out.println(name + " preparing;");
    }
}
