package com.bd.ace.trade.dao;

import com.bd.ace.trade.dao.entity.TradeUser;
import com.bd.ace.trade.dao.mapper.TradeUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-dao.xml")
public class DaoTest {
    @Autowired
    private TradeUserMapper tradeUserMapper;

    @Test
    public void test() {
        TradeUser record = new TradeUser();
        record.setUserName("pyl");
        record.setUserPassword("Pyl123456");
        tradeUserMapper.insert(record);
        System.out.println("id = " + record.getUserId());
    }
}
