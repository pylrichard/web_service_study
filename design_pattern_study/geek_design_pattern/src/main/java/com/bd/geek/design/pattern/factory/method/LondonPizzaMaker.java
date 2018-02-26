package com.bd.geek.design.pattern.factory.method;

import com.bd.geek.design.pattern.factory.pizza.LondonCheesePizza;
import com.bd.geek.design.pattern.factory.pizza.LondonPepperPizza;
import com.bd.geek.design.pattern.factory.pizza.Pizza;

/**
 * 不要让类继承自具体类，要继承自抽象类或接口
 */
public class LondonPizzaMaker extends PizzaMaker {
    @Override
    Pizza createPizza(String pizzaType) {
        Pizza pizza = null;
        if (pizzaType.equals("cheese")) {
            pizza = new LondonCheesePizza();
        } else if (pizzaType.equals("pepper")) {
            pizza = new LondonPepperPizza();
        }

        return pizza;
    }
}
