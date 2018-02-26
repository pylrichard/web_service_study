package com.bd.geek.design.pattern.factory.simple.factory;

import com.bd.geek.design.pattern.factory.pizza.CheesePizza;
import com.bd.geek.design.pattern.factory.pizza.ChinesePizza;
import com.bd.geek.design.pattern.factory.pizza.GreekPizza;
import com.bd.geek.design.pattern.factory.pizza.Pizza;

/**
 * 简单工厂模式，封装实例化对象的行为
 */
public class SimplePizzaFactory {
    public Pizza createPizza(String pizzaType) {
        Pizza pizza = null;
        if (pizzaType.equals("cheese")) {
            pizza = new CheesePizza();
        } else if (pizzaType.equals("greek")) {
            pizza = new GreekPizza();
        } else if (pizzaType.equals("chinese")) {
            pizza = new ChinesePizza();
        }

        return pizza;
    }
}
