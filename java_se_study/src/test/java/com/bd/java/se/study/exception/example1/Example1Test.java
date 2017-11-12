package com.bd.java.se.study.exception.example1;

import com.bd.java.se.study.exception.example1.Exception1;

public class Example1Test {
    public static void main(String[] args) {
        Exception1 ex = new Exception1();
        //ex.method1();
        int ret = ex.method5();
        System.out.println("main ret " + ret);
    }
}
