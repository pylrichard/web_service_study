package com.bd.geek.design.pattern.factory.store;

import com.bd.geek.design.pattern.factory.pizza.*;

public class PizzaMaker {
    public PizzaMaker() {
        Pizza pizza = null;
        String pizzaType;
        do {
            pizzaType = PizzaSeller.getType();
            /*
                添加/删除/修改Pizza，需要修改此处代码
             */
            if (pizzaType.equals("cheese")) {
                pizza = new CheesePizza();
            } else if (pizzaType.equals("greek")) {
                pizza = new GreekPizza();
            } else if (pizzaType.equals("chinese")) {
                pizza = new ChinesePizza();
            } else {
                break;
            }
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        } while (true);
    }
}
