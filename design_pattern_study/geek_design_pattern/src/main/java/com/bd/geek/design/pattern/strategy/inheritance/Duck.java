package com.bd.geek.design.pattern.strategy.inheritance;

public abstract class Duck {
    public Duck() {
    }

    public void quack() {
        System.out.println("quack");
    }

    public abstract void display();

    public void swim() {
        System.out.println("swim");
    }

    public void fly() {
        System.out.println("fly");
    }
}
