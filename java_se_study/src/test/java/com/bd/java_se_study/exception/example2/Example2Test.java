package com.bd.java_se_study.exception.example2;

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
