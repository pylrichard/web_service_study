package com.bd.java.se.study.exception.example2;

public class ChainException {
    //异常抛给更上一层的调用者
    public void method1() throws DrunkException {
        //抛出异常
        throw new DrunkException("throw DrunkException");
    }

    public void method2() {
        try {
            method1();
        } catch (DrunkException e) {
            RuntimeException re = new RuntimeException("catch RuntimeException");
            //引用原始异常，实现异常链
            re.initCause(e);

            //将DrunkException封装成RuntimeException，更简便
            //RuntimeException re = new RuntimeException(e);

            throw re;
        }
    }
}
