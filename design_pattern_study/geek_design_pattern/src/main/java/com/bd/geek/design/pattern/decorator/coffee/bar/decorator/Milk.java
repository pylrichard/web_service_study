package com.bd.geek.design.pattern.decorator.coffee.bar.decorator;

import com.bd.geek.design.pattern.decorator.coffee.bar.Drink;

public class Milk extends Decorator {
    public Milk(Drink obj) {
        super(obj);
        super.setDescription("Milk");
        super.setPrice(2.0f);
    }
}

