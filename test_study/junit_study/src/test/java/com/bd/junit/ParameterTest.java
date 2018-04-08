package com.bd.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * 1.更改测试运行器为Parameterized.class
 * 2.声明变量存放预期值和结果值
 * 3.声明一个返回值为Collection的公共静态方法t()，使用@Parameters进行修饰
 * 4.为测试类声明一个带有参数的公共构造函数ParameterTest()，并在其中为声明变量赋值
 */
@RunWith(Parameterized.class)
public class ParameterTest {
    int expected =0;
    int input1 = 0;
    int input2 = 0;

    /**
     * 测试数据
     */
    @Parameterized.Parameters
    public static Collection<Object[]> t() {
        return Arrays.asList(new Object[][]{
                {3, 1, 2},
                {4, 2, 2}
        });
    }

    public ParameterTest(int expected, int input1, int input2) {
        this.expected = expected;
        this.input1 = input1;
        this.input2 = input2;
    }

    @Test
    public void testAdd() {
        assertEquals(expected, new Calculate().add(input1, input2));
    }
}
