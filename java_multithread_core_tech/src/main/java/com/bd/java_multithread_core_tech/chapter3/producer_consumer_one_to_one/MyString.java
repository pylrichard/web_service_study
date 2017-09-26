package com.bd.java_multithread_core_tech.chapter3.producer_consumer_one_to_one;

public class MyString {
    private static String value = "";

    public static boolean isEmpty() {
        return value.equals("");
    }

    public static void setStr(String str) {
        value = str;
    }

    public static String getStr() {
        return value;
    }
}
