package com.bd.ace.trade.common.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;

public interface IMessageProcessor {
    boolean handleMessage(MessageExt messageExt);
}
