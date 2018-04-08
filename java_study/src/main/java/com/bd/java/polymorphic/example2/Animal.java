package com.bd.java.polymorphic.example2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Animal {
    private String name;

    static {
        System.out.println("animal static");
    }

    public Animal() {
        System.out.println("animal construct");
    }

    public static void say() {
        System.out.println("animal say");
    }

    public void eat() {
        System.out.println("animal say");
    }
}
