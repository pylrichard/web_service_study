package com.bd.geek.design.pattern.strategy.combination.behavior.quack;

public class BadQuackBehavior implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("BadQuackBehavior");
    }
}
