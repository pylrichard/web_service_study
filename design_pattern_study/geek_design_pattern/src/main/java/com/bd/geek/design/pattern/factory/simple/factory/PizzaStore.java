package com.bd.geek.design.pattern.factory.simple.factory;

public class PizzaStore {
    public static void main(String[] args) {
        PizzaMaker pizzaMaker = new PizzaMaker(new SimplePizzaFactory());
    }
}
