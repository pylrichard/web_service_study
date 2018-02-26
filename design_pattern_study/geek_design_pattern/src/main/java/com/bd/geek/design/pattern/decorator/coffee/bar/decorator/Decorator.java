package com.bd.geek.design.pattern.decorator.coffee.bar.decorator;

import com.bd.geek.design.pattern.decorator.coffee.bar.Drink;

public class Decorator extends Drink {
    /**
     * 被装饰类对象
     */
    private Drink obj;

    public Decorator(Drink obj) {
        this.obj = obj;
    }

    @Override
    public float cost() {
        return super.getPrice() + obj.cost();
    }

    @Override
    public String getDescription() {
        return super.description + "-" + super.getPrice() + "&&" + obj.getDescription();
    }
}
