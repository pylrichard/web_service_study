package com.bd.ace.trade.common.rocketmq;

import com.bd.ace.trade.common.constant.MqEnums;
import com.bd.ace.trade.common.exception.AceMqException;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:xml/spring-rocketmq-producer.xml")
public class AceMqProducerTest {
    @Autowired
    private AceMqProducer aceMqProducer;

    @Test
    public void testProducer() throws AceMqException {
        SendResult sendResult = aceMqProducer.sendMessage(MqEnums.TopicEnum.ORDER_CONFIRM,
                                                    "123456", "this is a order msg");
        System.out.println(sendResult);
    }
}
