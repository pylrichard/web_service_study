package com.bd.imooc.spring.aop.api;

import com.bd.imooc.spring.base.UnitTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAOPAPI extends UnitTestBase {
    public TestAOPAPI() {
        super("classpath:spring-aop-api.xml");
    }

    @Test
    public void testSave() {
        //见spring-aop-api.xml的<ref bean="bizLogicImplTarget"/>
        BizLogic logic = (BizLogic) super.getBean("bizLogicImpl");
        logic.save();
    }
}
