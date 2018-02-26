package com.bd.geek.design.pattern.factory.simple.factory;

import com.bd.geek.design.pattern.factory.pizza.Pizza;
import com.bd.geek.design.pattern.factory.pizza.PizzaSeller;

public class PizzaMaker {
    SimplePizzaFactory simplePizzaFactory;

    public PizzaMaker(SimplePizzaFactory simplePizzaFactory) {
        setFactory(simplePizzaFactory);
    }

    public void setFactory(SimplePizzaFactory simplePizzaFactory) {
        Pizza pizza = null;
        String pizzaType;
        this.simplePizzaFactory = simplePizzaFactory;
        do {
            pizzaType = PizzaSeller.getType();
            pizza = simplePizzaFactory.createPizza(pizzaType);
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        } while (true);
    }
}
