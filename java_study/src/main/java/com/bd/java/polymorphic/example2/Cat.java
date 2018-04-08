package com.bd.java.polymorphic.example2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cat extends Animal {
    static int RED = 1 << 1;
    static int BLUE = 1 << 2;
    static int YELLOW = 1 << 3;
    private String name;

    /**
     * 类加载后只执行一次
     */
    static {
        System.out.println("cat static");
    }

    public Cat() {
        System.out.println("cat construct");
    }

    public static void say() {
        System.out.println("cat say");
    }

    @Override
    public void eat() {
        System.out.println("cat eat");
    }

    public static void change(int age, Integer score, Cat cat, Animal animal) {
        age = 19;
        score = 100;
        cat = new Cat();
        cat.setName("lucy");
        animal.setName("jerry");
    }

    public static void main(String[] args) {
        Animal animal = new Cat();
        Animal.say();
        animal.eat();
        Cat cat = new Cat();
        Cat.say();
        cat.eat();

        String str1 = "hello";
        String str2 = new String("hello");
        String str3 = "hello";
        System.out.println(str1 == str2);
        System.out.println(str1 == str3);

        int age = 17;
        Integer score = 19;
        cat = new Cat();
        cat.setName("tom");
        animal = new Animal();
        animal.setName("jack");
        change(age, score, cat, animal);
        System.out.println(age);
        System.out.println(score);
        System.out.println(cat.getName());
        System.out.println(animal.getName());

        System.out.println(Cat.RED + Cat.BLUE);
        System.out.println(Cat.RED | Cat.BLUE);
        System.out.println(Cat.RED & Cat.YELLOW);
    }
}
