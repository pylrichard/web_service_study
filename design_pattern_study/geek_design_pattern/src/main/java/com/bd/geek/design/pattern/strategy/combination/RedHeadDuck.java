package com.bd.geek.design.pattern.strategy.combination;

import com.bd.geek.design.pattern.strategy.combination.behavior.fly.BadFlyBehavior;
import com.bd.geek.design.pattern.strategy.combination.behavior.quack.BadQuackBehavior;

public class RedHeadDuck extends Duck {
    public RedHeadDuck() {
        flyBehavior = new BadFlyBehavior();
        quackBehavior = new BadQuackBehavior();
    }

    @Override
    public void display() {
        System.out.println("RedHeadDuck");
    }
}
