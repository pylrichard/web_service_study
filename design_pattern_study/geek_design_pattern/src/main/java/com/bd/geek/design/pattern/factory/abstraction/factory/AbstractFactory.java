package com.bd.geek.design.pattern.factory.abstraction.factory;

import com.bd.geek.design.pattern.factory.pizza.Pizza;

/**
 * 抽象工厂模式定义一个接口用于创建对象，而无需明确指定具体类
 */
public interface AbstractFactory {
    Pizza createPizza(String pizzaType);
}
