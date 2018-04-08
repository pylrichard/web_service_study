package com.bd.imooc.spring.primer.bean.annotation;

import com.bd.imooc.spring.base.UnitTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanAnnotation extends UnitTestBase {
    public TestBeanAnnotation() {
        super("classpath*:spring-bean-annotation.xml");
    }

    @Test
    public void testSay() {
        //默认Bean名称为类首字母小写的类名
        BeanAnnotation bean = super.getBean("beanAnnotation");
        bean.say("This is test.");

        //自定义Bean名称
        bean = super.getBean("bean");
        bean.say("This is test.");
    }

    @Test
    public void testScope() {
        BeanAnnotation bean = super.getBean("beanAnnotation");
        bean.myHashCode();

        bean = super.getBean("beanAnnotation");
        bean.myHashCode();
    }
}
