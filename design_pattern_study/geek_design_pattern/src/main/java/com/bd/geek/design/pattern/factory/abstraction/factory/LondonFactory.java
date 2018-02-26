package com.bd.geek.design.pattern.factory.abstraction.factory;

import com.bd.geek.design.pattern.factory.pizza.LondonCheesePizza;
import com.bd.geek.design.pattern.factory.pizza.LondonPepperPizza;
import com.bd.geek.design.pattern.factory.pizza.Pizza;

public class LondonFactory implements AbstractFactory {
    @Override
    public Pizza createPizza(String pizzaType) {
        Pizza pizza = null;
        if (pizzaType.equals("cheese")) {
            pizza = new LondonCheesePizza();
        } else if (pizzaType.equals("pepper")) {
            pizza = new LondonPepperPizza();
        }

        return pizza;
    }
}
