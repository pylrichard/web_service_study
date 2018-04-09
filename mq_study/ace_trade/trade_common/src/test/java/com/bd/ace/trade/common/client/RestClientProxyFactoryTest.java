package com.bd.ace.trade.common.client;

import com.bd.ace.trade.common.api.IUserApi;
import com.bd.ace.trade.common.protocol.user.QueryUserReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:xml/spring-rest-client.xml")
public class RestClientProxyFactoryTest {
    @Autowired
    private IUserApi userApi;

    @Test
    public void test() {
        QueryUserReq queryUserReq = new QueryUserReq();
        queryUserReq.setUserId(1);
        System.out.println(userApi.queryUser(queryUserReq));
    }
}
