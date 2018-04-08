package com.bd.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 测试套件组织测试类一起运行
 *
 * 1.写一个作为测试套件的入口类，不包含任何方法
 * 2.注解@RunWith更改测试运行器为Suite.class，继承org.junit.runner.Runner
 * 3.将测试用例类作为数组传入到Suite.SuiteClasses
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TaskTest1.class,TaskTest2.class,TaskTest3.class})
public class SuiteTest {}