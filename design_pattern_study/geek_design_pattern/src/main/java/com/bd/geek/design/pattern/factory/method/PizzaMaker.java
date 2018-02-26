package com.bd.geek.design.pattern.factory.method;

import com.bd.geek.design.pattern.factory.pizza.Pizza;
import com.bd.geek.design.pattern.factory.pizza.PizzaSeller;

public abstract class PizzaMaker {
    public PizzaMaker() {
        Pizza pizza = null;
        String pizzaType;
        do {
            pizzaType = PizzaSeller.getType();
            //变量不要持有具体类的引用
            pizza = createPizza(pizzaType);
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        } while (true);
    }

    /**
     * 定义一个创建对象的抽象方法，由子类决定要实例化的类，工厂方法模式将对象的实例化推迟到子类
     * 子类不要覆盖基类中已实现的方法(所有子类通用)
     */
    abstract Pizza createPizza(String pizzaType);
}
