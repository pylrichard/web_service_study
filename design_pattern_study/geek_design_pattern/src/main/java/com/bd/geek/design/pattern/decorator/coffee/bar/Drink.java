package com.bd.geek.design.pattern.decorator.coffee.bar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Drink {
    public String description = "";
    private float price = 0f;

    public String getDescription() {
        return description + "-" + this.getPrice();
    }

    public abstract float cost();
}
