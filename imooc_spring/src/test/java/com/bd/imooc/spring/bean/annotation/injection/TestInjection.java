package com.bd.imooc.spring.bean.annotation.injection;

import com.bd.imooc.spring.base.UnitTestBase;
import com.bd.imooc.spring.bean.annotation.injection.service.InjectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestInjection extends UnitTestBase {
    public TestInjection() {
        super("classpath:spring-bean-annotation.xml");
    }

    @Test
    public void testAutowired() {
        InjectionService service = super.getBean("injectionServiceImpl");
        service.save("This is autowired.");
    }
}
