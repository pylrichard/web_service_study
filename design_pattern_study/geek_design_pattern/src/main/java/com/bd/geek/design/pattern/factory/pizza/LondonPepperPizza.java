package com.bd.geek.design.pattern.factory.pizza;

public class LondonPepperPizza extends Pizza {
    @Override
    public void prepare() {
        super.setName("LondonPepperPizza");
        System.out.println(name + " preparing;");
    }
}
