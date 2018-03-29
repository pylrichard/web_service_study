package com.bd.java.multithread.core.tech.chapter3.producer_consumer_all_wait;

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
