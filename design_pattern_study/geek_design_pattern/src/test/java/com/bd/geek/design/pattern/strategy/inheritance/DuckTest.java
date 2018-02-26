package com.bd.geek.design.pattern.strategy.inheritance;

public class DuckTest {
    public static void main(String[] args) {
        GreenHeadDuck greenHeadDuck = new GreenHeadDuck();
        RedHeadDuck redHeadDuck = new RedHeadDuck();
        StoneDuck stoneDuck = new StoneDuck();
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
    }
}