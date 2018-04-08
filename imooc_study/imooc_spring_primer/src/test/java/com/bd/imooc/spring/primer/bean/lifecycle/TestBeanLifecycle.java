package com.bd.imooc.spring.primer.bean.lifecycle;

import com.bd.imooc.spring.primer.base.UnitTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanLifecycle extends UnitTestBase {
    public TestBeanLifecycle() {
        super("classpath:spring-bean-lifecycle.xml");
    }

    @Test
    public void test() {
        super.getBean("beanLifeCycle");
    }
}
