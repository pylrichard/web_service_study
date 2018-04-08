package com.bd.imooc.spring.primer.aop.aspectj;

import com.bd.imooc.spring.aop.aspectj.biz.MoocBiz;
import com.bd.imooc.spring.base.UnitTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAspectJ extends UnitTestBase {
    public TestAspectJ() {
        super("classpath:spring-aop-aspectj.xml");
    }

    @Test
    public void test() {
        MoocBiz biz = getBean("moocBiz");
        biz.save("This is test.");
    }
}
