package com.bd.ace.trade.common.rocketmq;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class AceMessageListener implements MessageListenerConcurrently {
	private IMessageProcessor messageProcessor;

	public void setMessageProcessor(IMessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		for (MessageExt msg : msgs) {
			boolean result = messageProcessor.handleMessage(msg);
			if (!result) {
				//有一条消息处理出错，这一批消息稍后再次进行处理
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
		}

		//这一批消息全部处理成功
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
