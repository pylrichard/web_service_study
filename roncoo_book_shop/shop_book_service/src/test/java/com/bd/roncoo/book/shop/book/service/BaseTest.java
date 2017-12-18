package com.bd.roncoo.book.shop.book.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//SpringRunner运行测试用例
@RunWith(SpringRunner.class)
//通知Spring Boot，要测试的程序入口
@SpringBootTest(classes = BookServiceApplication.class)
//所有的测试用例在一个事务下执行，执行的数据库操作会进行回滚，保证测试用例可以反复执行
@Transactional
public class BaseTest {
}
