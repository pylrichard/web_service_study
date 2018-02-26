package com.bd.geek.design.pattern.strategy.combination.behavior.fly;

public class GoodFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("GoodFlyBehavior");
    }
}