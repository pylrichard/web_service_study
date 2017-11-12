package com.bd.junit.study;

import com.bd.junit.study.Calculate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 1.Failure一般由单元测试使用的断言方法判断失败所引起的，表示测试点发现了问题，就是程序输出结果和预期的不一样
 * 2.Error是由代码异常引起的，它可以产生于测试代码本身的错误，也可以是被测试代码中的一个隐藏的bug
 * 3.测试用例不是用来证明代码是对的，而是用来证明代码没有错
 */
public class ErrorAndFailureTest {
    /**
     * Failure
     */
    @Test
    public void testAdd() throws Exception {
        assertEquals(5, new Calculate().add(3,3));
    }

    /**
     * Error，抛出异常
     */
    @Test
    public void testDivide() throws Exception {
        assertEquals(3, new Calculate().divide(6, 0));
    }
}
