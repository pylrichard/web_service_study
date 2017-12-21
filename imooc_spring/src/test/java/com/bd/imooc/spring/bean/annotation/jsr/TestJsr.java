package com.bd.imooc.spring.bean.annotation.jsr;

import com.bd.imooc.spring.base.UnitTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestJsr extends UnitTestBase {
    public TestJsr() {
        super("classpath*:spring-bean-annotation.xml");
    }

    @Test
    public void testSave() {
        JsrService service = getBean("jsrService");
        service.save();
    }
}
