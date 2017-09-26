package com.bd.java_se_study.exception.example1;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
    Throwable
    Error继承于Throwable
        VirtualMachineError 虚拟机错误
        ThreadDeath 线程死锁
    Exception继承于Throwable
        RuntimeException 非检查异常，JVM自动捕获，自动抛出
            NullPointerException 空指针异常
                String str = null;
                System.out.println(str.length());
            ArrayIndexOutOfBoundsException 数组下标越界异常
            ClassCastException 类型转换异常
                class Animal {}
                class Dog extends Animal {}
                class Cat extends Animal {}
                public class Test {
                    public static void main(String[] args) {
                        Animal a1 = new Dog();
                        Animal a2 = new Cat();
                        Dog d1 = (Dog)a1;
                        Dog d2 = (Dog)a2;
                    }
                }
            ArithmeticException 算术异常
        IOException/SQLException 检查异常，需要手动添加捕获/处理语句
 */
public class Exception1 {
    public void method1() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("first: ");
            int first = input.nextInt();
            System.out.println("second: ");
            int second = input.nextInt();
            System.out.println("result: " + first / second);
        } catch (InputMismatchException e1) {
            //first为字符触发此异常
            System.out.println("InputMismatchException");
        } catch (ArithmeticException e2) {
            //second为0触发此异常
            System.out.println("ArithmeticException");
        } catch (Exception e3){
            //处理可能遗漏的异常
            //多重catch块要注意异常处理顺序问题，先处理子类异常(ArithmeticException)，再处理父类异常(Exception)
            //异常处理系统就近匹配catch块，针对父类异常的catch块可以处理子类异常
            //Exception的catch块放在最前面，编译器会提示错误
            System.out.println("Other Exception");

            //不仅是打印异常堆栈，根据业务需求和异常类型去处理异常
            e3.printStackTrace();
        } finally {
            //释放占用的资源，如网络链接，数据库链接
        }
    }

    public int method2() {
        int divider = 10;
        int result = 100;

        try {
            while(divider > -1) {
                divider--;
                //此处触发异常，进入catch块
                result += 100 / divider;
            }

            //此处的返回语句不会执行
            System.out.println("try return");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch loop error");
            //调用者得到此处的返回值
            return -1;
        } finally {
            //finally在try块和catch块执行完毕，返回到调用者之前执行的
            System.out.println("finally result = " + result);
        }
    }

    public int method3() {
        int divider = 10;
        int result = 100;

        try {
            while(divider > -1) {
                divider--;
                result += 100 / divider;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch loop error");
            return -1;
        } finally {
            System.out.println("finally result = " + result);
            //调用者得到此处的返回值
            return -2;
        }

        //catch、finally块中都有return语句，此处不能有返回值，会报错
        //return -3;
    }

    public int method4() {
        int divider = 10;
        int result = 100;

        try {
            while(divider > -1) {
                divider--;
                result += 100 / divider;
            }

            //try块中不能有返回值，否则会报错
            //return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch loop error");
            //调用者得到此处的返回值
            return -1;
        } finally {
            System.out.println("finally result = " + result);
        }

        //catch块中有return语句，不会返回此处的值
        return -3;
    }

    public int method5() {
        int divider = 10;
        int result = 100;

        try {
            while(divider > -1) {
                divider--;
                result += 100 / divider;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch loop error");
        } finally {
            System.out.println("finally result = " + result);
        }

        //catch、finally块中都没有return语句，返回此处的值
        return -3;
    }
}
