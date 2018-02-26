package com.bd.geek.design.pattern.factory.pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PizzaSeller {
    public static String getType() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String type = bufferedReader.readLine();

            return type;
        } catch (IOException e) {
            e.printStackTrace();

            return "";
        }
    }
}
