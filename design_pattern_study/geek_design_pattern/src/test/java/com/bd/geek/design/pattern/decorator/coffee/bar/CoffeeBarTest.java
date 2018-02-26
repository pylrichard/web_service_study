package com.bd.geek.design.pattern.decorator.coffee.bar;

import com.bd.geek.design.pattern.decorator.coffee.bar.coffee.Decaf;
import com.bd.geek.design.pattern.decorator.coffee.bar.coffee.LongBlack;
import com.bd.geek.design.pattern.decorator.coffee.bar.decorator.Chocolate;
import com.bd.geek.design.pattern.decorator.coffee.bar.decorator.Milk;

public class CoffeeBarTest {
    public static void main(String[] args) {
        Drink drink;
        drink = new Decaf();
        System.out.println("order1 price:" + drink.cost());
        System.out.println("order1 desc:" + drink.getDescription());
        //优先使用组合
        drink = new LongBlack();
        drink = new Milk(drink);
        drink = new Chocolate(drink);
        drink = new Chocolate(drink);
        System.out.println("order2 price:" + drink.cost());
        System.out.println("order2 desc:" + drink.getDescription());
    }
}