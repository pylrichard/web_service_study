package com.bd.geek.design.pattern.factory.abstraction.factory;

import com.bd.geek.design.pattern.factory.pizza.Pizza;
import com.bd.geek.design.pattern.factory.pizza.PizzaSeller;

public class PizzaMaker {
    AbstractFactory abstractFactory;

    public PizzaMaker(AbstractFactory abstractFactory) {
        setFactory(abstractFactory);
    }

    public void setFactory(AbstractFactory abstractFactory) {
        Pizza pizza = null;
        String pizzaType;
        this.abstractFactory = abstractFactory;
        do {
            pizzaType = PizzaSeller.getType();
            pizza = abstractFactory.createPizza(pizzaType);
            if (pizza != null) {
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }
        } while (true);
    }
}
