package com.bd.ace.trade.common.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;

/**
 * 在AceMessageListener.consumeMessage()中调用
 */
public class ProcessorTest implements IMessageProcessor {
    @Override
    public boolean handleMessage(MessageExt messageExt) {
        System.out.println("收到消息 " + messageExt.toString());
        return true;
    }
}
