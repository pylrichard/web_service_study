package com.bd.ace.trade.common.rocketmq;

import com.bd.ace.trade.common.exception.AceMqException;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AceMqConsumer {
	public static final Logger logger = LoggerFactory.getLogger(AceMqConsumer.class);

	private static final int CONSUME_THREAD_MIN = 20;
	private static final int CONSUME_THREAD_MAX = 64;
	private String topic;
	/**
	 * "*"表示订阅所有的tag，多个tag以||分隔
	 */
	private String tag = "*";
	private String groupName;
	private String namesrvAddr;
    private IMessageProcessor processor;
    
    public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public IMessageProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(IMessageProcessor processor) {
		this.processor = processor;
	}

	public void init() throws AceMqException {
		if (StringUtils.isBlank(groupName)) {
			throw new AceMqException("groupName is blank!");
		}
		if (StringUtils.isBlank(topic)) {
			throw new AceMqException("topic is blank!");
		}
		if (StringUtils.isBlank(namesrvAddr)) {
			throw new AceMqException("namesrvAddr is blank!");
		}
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.groupName);
    	consumer.setNamesrvAddr(this.namesrvAddr);
    	try {
			consumer.subscribe(this.topic, tag);
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
	    	consumer.setConsumeThreadMin(CONSUME_THREAD_MIN);
	    	consumer.setConsumeThreadMax(CONSUME_THREAD_MAX);
	    	AceMessageListener listener = new AceMessageListener();
	    	listener.setMessageProcessor(processor);
	    	consumer.registerMessageListener(listener);
	    	consumer.start();
	    	logger.info(String.format("consumer is start! groupName:{%s}, topic:{%s}, namesrvAddr:{%s}",
					this.groupName, this.topic, this.namesrvAddr));
		} catch (MQClientException e) {
			logger.error(String.format("consumer error! groupName:{%s}, topic:{%s}, namesrvAddr:{%s} exception:{%s}",
					this.groupName, this.topic, this.namesrvAddr, e));
			throw new AceMqException(e);
		}
    }
}
