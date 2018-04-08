package com.bd.imooc.spring.primer.bean.scope;

import com.bd.imooc.spring.base.UnitTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanScope extends UnitTestBase {
    public TestBeanScope() {
        super("classpath*:spring-bean-scope.xml");
    }

    /**
     * spring-bean-scope.xml测试singleton/prototype，见8~9.png
     */
    @Test
    public void testSay1() {
        BeanScope beanScope1 = super.getBean("beanScope");
        beanScope1.say();

        /*
        	singleton得到相同的Bean
        	prototype得到不同的Bean
         */
        BeanScope beanScope2 = super.getBean("beanScope");
        beanScope2.say();
    }

    /**
     * 运行单元测试方法，会依次执行UnitTestBase.before()、单元测试方法、UnitTestBase.after()
     * 会创建1个新的Bean容器，见10.png
     */
    @Test
    public void testSay2() {
        //singleton下此处的Bean容器与testSay1()的不同
        BeanScope beanScope = super.getBean("beanScope");
        beanScope.say();
    }
}
