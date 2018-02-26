package com.bd.geek.design.pattern.decorator.coffee.bar.coffee;

import com.bd.geek.design.pattern.decorator.coffee.bar.Drink;

public class Coffee extends Drink {
    @Override
    public float cost() {
        return super.getPrice();
    }
}
