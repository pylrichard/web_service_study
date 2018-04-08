package com.bd.java.polymorphic.example1;

public class Example1Test {
    public static void main(String[] args) {
        Animal obj1 = new Animal();
        //父类引用可以指向子类对象
        Animal obj2 = new Dog();
        Animal obj3 = new Cat();

        //调用本身的方法
        obj1.eat();
        //调用子类重写的方法
        obj2.eat();
        //调用父类的方法
        obj3.eat();
        //不能通过父类引用调用子类独有方法
        //obj2.watchDoor();
        //子类引用不可以指向父类对象
        //Dog obj3 = new Animal();
    }
}
