package com.bd.geek.design.pattern.strategy.inheritance;

public class RedHeadDuck extends Duck {
    @Override
    public void display() {
        System.out.println("RedHeadDuck");
    }

    @Override
    public void fly() {
        System.out.println("good fly");
    }
}
