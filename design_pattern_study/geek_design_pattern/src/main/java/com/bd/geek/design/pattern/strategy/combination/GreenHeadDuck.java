package com.bd.geek.design.pattern.strategy.combination;

import com.bd.geek.design.pattern.strategy.combination.behavior.fly.GoodFlyBehavior;
import com.bd.geek.design.pattern.strategy.combination.behavior.quack.GoodQuackBehavior;

public class GreenHeadDuck extends Duck {
    public GreenHeadDuck() {
        flyBehavior = new GoodFlyBehavior();
        quackBehavior = new GoodQuackBehavior();
    }

    @Override
    public void display() {
        System.out.println("GreenHeadDuck");
    }
}