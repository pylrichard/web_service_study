package com.bd.geek.design.pattern.decorator.coffee.bar.decorator;

import com.bd.geek.design.pattern.decorator.coffee.bar.Drink;

public class Chocolate extends Decorator {
    public Chocolate(Drink obj) {
        super(obj);
        super.setDescription("Chocolate");
        super.setPrice(3.0f);
    }
}
