package com.bd.imooc.spring.ioc.injection;

import com.bd.imooc.spring.base.UnitTestBase;
import com.bd.imooc.spring.ioc.injection.service.InjectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestInjection extends UnitTestBase {
    public TestInjection() {
        super("classpath:spring-injection.xml");
    }

    //测试构造器注入/设值注入
    @Test
    public void test() {
        InjectionService service = super.getBean("injectionService");
        service.save("这是要保存的数据");
    }
}
