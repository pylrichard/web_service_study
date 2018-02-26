package com.bd.geek.design.pattern.strategy.combination;

import com.bd.geek.design.pattern.strategy.combination.behavior.fly.NoFlyBehavior;
import com.bd.geek.design.pattern.strategy.combination.behavior.quack.NoQuackBehavior;

public class StoneDuck extends Duck {
    public StoneDuck() {
        flyBehavior = new NoFlyBehavior();
        quackBehavior = new NoQuackBehavior();
    }

    @Override
    public void display() {
        System.out.println("StoneDuck");
    }
}
