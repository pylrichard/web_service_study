package com.bd.imooc.study.aop.service.sub;

import com.bd.imooc.study.aop.service.sub.SubService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubServiceTest {
    @Autowired
    private SubService subService;

    @Test
    public void method() throws Exception {
        subService.method();
    }
}