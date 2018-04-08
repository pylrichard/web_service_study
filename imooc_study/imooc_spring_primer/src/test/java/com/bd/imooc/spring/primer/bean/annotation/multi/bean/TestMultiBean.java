package com.bd.imooc.spring.primer.bean.annotation.multi.bean;

import com.bd.imooc.spring.base.UnitTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestMultiBean extends UnitTestBase {
    public TestMultiBean() {
        super("classpath*:spring-bean-annotation.xml");
    }

    @Test
    public void testMultiBean() {
        BeanInvoker invoker = super.getBean("beanInvoker");
        invoker.say();
    }
}
