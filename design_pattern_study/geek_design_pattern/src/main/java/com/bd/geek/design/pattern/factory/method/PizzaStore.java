package com.bd.geek.design.pattern.factory.method;

public class PizzaStore {
    public static void main(String[] args) {
        PizzaMaker pizzaMaker = new NewYorkPizzaMaker();
    }
}
