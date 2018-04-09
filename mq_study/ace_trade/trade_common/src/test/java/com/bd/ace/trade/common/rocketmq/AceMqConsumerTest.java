package com.bd.ace.trade.common.rocketmq;

import com.bd.ace.trade.common.exception.AceMqException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:xml/spring-rocketmq-consumer.xml")
public class AceMqConsumerTest {
    @Autowired
    private AceMqConsumer aceMqConsumer;

    @Test
    public void testConsumer() throws AceMqException, InterruptedException {
        Thread.sleep(1000L);
    }
}
