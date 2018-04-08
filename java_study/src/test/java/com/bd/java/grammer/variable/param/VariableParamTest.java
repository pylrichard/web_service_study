package com.bd.java.grammer.variable.param;

public class VariableParamTest {
    public static void main(String[] args) {
        print("000", "111", "222", "333");
    }

    //可变长度参数必须作为方法参数列表中的的最后一个参数且方法参数列表中只能有一个可变长度参数
    public static void print(String... params) {
        for (int i = 0; i < params.length; i++) {
            System.out.println(params[i]);
        }
    }
}
