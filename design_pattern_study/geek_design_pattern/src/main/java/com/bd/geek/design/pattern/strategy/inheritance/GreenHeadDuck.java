package com.bd.geek.design.pattern.strategy.inheritance;

public class GreenHeadDuck extends Duck {
    @Override
    public void display() {
        System.out.println("GreenHeadDuck");
    }

    @Override
    public void fly() {
        System.out.println("bad fly");
    }
}
