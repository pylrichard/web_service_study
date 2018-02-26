package com.bd.geek.design.pattern.factory.pizza;

public class NewYorkPepperPizza extends Pizza {
    @Override
    public void prepare() {
        super.setName("NewYorkPepperPizza");
        System.out.println(name + " preparing;");
    }
}
