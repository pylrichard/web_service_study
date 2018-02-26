package com.bd.geek.design.pattern.strategy.combination.behavior.fly;

public class NoFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("NoFlyBehavior");
    }
}
