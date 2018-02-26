package com.bd.geek.design.pattern.strategy.inheritance;

public class StoneDuck extends Duck {
    @Override
    public void display() {
        System.out.println("StoneDuck");
    }

    @Override
    public void quack() {
        System.out.println("no quack");
    }

    @Override
    public void swim() {
        System.out.println("no swim");
    }

    @Override
    public void fly() {
        System.out.println("no fly");
    }
}
