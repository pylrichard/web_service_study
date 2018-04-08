package com.bd.imooc.spring.primer.bean.aware;

import com.bd.imooc.spring.base.UnitTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAware extends UnitTestBase {
    public TestAware() {
        super("classpath:spring-bean-aware.xml");
    }

    @Test
    public void testMoocApplicationContext() {
        System.out.println("testMoocApplicationContext : " + super.getBean("moocApplicationContext").hashCode());
    }

    @Test
    public void testMoocBeanName() {
        System.out.println("testMoocBeanName : " + super.getBean("moocBeanName").hashCode());
    }
}
