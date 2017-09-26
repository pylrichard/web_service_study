package com.bd.java_se_study.exception.example2;

/*
    自定义异常继承于内置异常
 */
public class DrunkException extends Exception {
    //声明有参数的构造函数，就不会默认声明无参的构造函数，可以手动添加无参的构造函数
    public DrunkException(String arg) {
        super(arg);
    }
}
