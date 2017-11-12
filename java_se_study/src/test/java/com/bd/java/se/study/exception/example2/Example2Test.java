package com.bd.java.se.study.exception.example2;

import com.bd.java.se.study.exception.example2.ChainException;

public class Example2Test {
    public static void main(String[] args) {
        ChainException ce = new ChainException();

        try {
            ce.method2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
