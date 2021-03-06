package com.bd.imooc.free.aop.service;

import com.bd.imooc.study.aop.security.CurrentUserHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test(expected = Exception.class)
    public void delete1() throws Exception {
        CurrentUserHolder.set("pyl");
        productService.delete(1L);
    }

    @Test
    public void delete2() {
        //权限检测不通过，触发异常
//        CurrentUserHolder.set("pyl");
        CurrentUserHolder.set("admin");
        productService.delete(1L);
    }

    @Test
    public void throwException() {
        try {
            productService.throwException();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getName() {
        productService.getName();
    }

    @Test
    public void log() {
        productService.log();
    }
}