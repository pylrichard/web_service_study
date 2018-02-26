package com.bd.geek.design.pattern.factory.abstraction.factory;

import com.bd.geek.design.pattern.factory.pizza.NewYorkCheesePizza;
import com.bd.geek.design.pattern.factory.pizza.NewYorkPepperPizza;
import com.bd.geek.design.pattern.factory.pizza.Pizza;

public class NewYorkFactory implements AbstractFactory {
    @Override
    public Pizza createPizza(String pizzaType) {
        Pizza pizza = null;
        if (pizzaType.equals("cheese")) {
            pizza = new NewYorkCheesePizza();
        } else if (pizzaType.equals("pepper")) {
            pizza = new NewYorkPepperPizza();
        }

        return pizza;
    }
}
