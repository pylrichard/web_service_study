package com.bd.java.se.study.annotation;

//@Description(desc = "Child")
public class Child extends Parent implements Person {
    @Override
    @Description(desc = "say", author = "richard")
    public void say() {}

    @Override
    public void walk() {}
}
