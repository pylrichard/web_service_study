package com.bd.geek.design.pattern.strategy.combination;

import com.bd.geek.design.pattern.strategy.combination.behavior.fly.NoFlyBehavior;
import com.bd.geek.design.pattern.strategy.combination.behavior.quack.NoQuackBehavior;

public class DuckTest {
    public static void main(String[] args) {
        /*
            此处要使用抽象父类Duck
         */
        Duck greenHeadDuck = new GreenHeadDuck();
        Duck redHeadDuck = new RedHeadDuck();
        Duck stoneDuck = new StoneDuck();
        greenHeadDuck.display();
        greenHeadDuck.fly();
        greenHeadDuck.quack();
        greenHeadDuck.swim();
        redHeadDuck.display();
        redHeadDuck.fly();
        redHeadDuck.quack();
        redHeadDuck.swim();
        stoneDuck.display();
        stoneDuck.fly();
        stoneDuck.quack();
        stoneDuck.swim();
        redHeadDuck.display();
        redHeadDuck.setFlyBehavior(new NoFlyBehavior());
        redHeadDuck.fly();
        redHeadDuck.setQuackBehavior(new NoQuackBehavior());
        redHeadDuck.quack();
    }
}