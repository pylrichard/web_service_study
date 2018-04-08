package com.bd.imooc.spring.primer.bean.annotation.java.based;

import com.bd.imooc.spring.base.UnitTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestJavaBased extends UnitTestBase {
    public TestJavaBased() {
        super("classpath*:spring-bean-annotation.xml");
    }

    @Test
    public void testStringStore() {
        Store store = super.getBean("stringStore");
        System.out.println(store.getClass().getName());
    }

    @Test
    public void testMyDriverManager() {
        MyDriverManager manager = super.getBean("myDriverManager");
        System.out.println(manager.getClass().getName());
    }

    @Test
    public void testScope() {
        Store store = super.getBean("stringStore");
        //store.getClass().getHashCode()是一致的
        System.out.println(store.hashCode());

        store = super.getBean("stringStore");
        System.out.println(store.hashCode());
    }

    @Test
    public void testCreateStore() {
        StringStore store = super.getBean("store");
    }
}
