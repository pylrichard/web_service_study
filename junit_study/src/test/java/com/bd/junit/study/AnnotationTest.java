package com.bd.junit.study;

import com.bd.junit.study.Calculate;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 注解@Test:将一个普通的方法修饰成为一个测试方法
 * 		如@Test(expected=XX.class)
 * 		如@Test(timeout=ms)，限制运行时间
 * 注解@BeforeClass:在所有的方法运行前被执行，static修饰
 * 注解@AfterClass:在所有的方法运行结束后被执行，static修饰
 * 注解@Before:在每一个测试方法运行前执行一次
 * 注解@After:在每一个测试方法运行后执行一次
 * 注解@Ignore:所修饰的测试方法会被测试运行器忽略
 */
public class AnnotationTest {
    /**
     * 添加expected=ArithmeticException.class不会报错
     */
    @Test(expected=ArithmeticException.class)
    public void testDivide() {
        assertEquals(3, new Calculate().divide(6, 0));
    }

    @Ignore("skip testWhile")
    @Test(timeout=2000)
    public void testWhile() {
        while (true) {
            System.out.println("run forever...");
        }
    }

    /**
     * 读取文件性能测试，超时则测试用例Failure
     */
    @Test(timeout=3000)
    public void testReadFile(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}