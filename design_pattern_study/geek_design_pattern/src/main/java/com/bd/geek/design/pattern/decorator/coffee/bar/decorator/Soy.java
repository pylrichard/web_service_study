package com.bd.geek.design.pattern.decorator.coffee.bar.decorator;

import com.bd.geek.design.pattern.decorator.coffee.bar.Drink;

public class Soy extends Decorator {
    public Soy(Drink obj) {
        super(obj);
        super.setDescription("Soy");
        super.setPrice(1.5f);
    }
}

