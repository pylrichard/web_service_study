package com.bd.imooc.study.aop;

import com.bd.imooc.study.aop.dao.ShopProductDAO;
import com.bd.imooc.study.aop.domain.ShopProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootAOPApplicationTest {
    @Autowired
    ShopProductDAO shopProductDAO;

    @Test
    public void testInsert() {
        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setName("dell xps");
        shopProduct.setOnlineTime(new Date());
        shopProduct.setBuyPrice(new BigDecimal("29.5"));
        shopProduct.setCategory("computer");
        shopProduct.setDetail("this is a dell notebook");
        shopProduct.setUpdateTime(new Date());
        shopProductDAO.save(shopProduct);
        System.out.println("new product id: " + shopProduct.getId());
    }

    @Test
    public void testUpdate() {
        ShopProduct product = shopProductDAO.findOne(3L);
        product.setName("mac book pro");
        product.setBuyPrice(new BigDecimal("23.5"));
        product.setOnlineTime(new Date());
        shopProductDAO.save(product);
    }

    @Test
    public void testDelete() {
        shopProductDAO.delete(3L);
    }
}