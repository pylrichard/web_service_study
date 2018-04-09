package com.bd.ace.trade.common.rocketmq;

import com.bd.ace.trade.common.constant.MqEnums;
import com.bd.ace.trade.common.exception.AceMqException;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * trade_order\src\main\resources\xml\spring-rocketmq-producer.xml
 * trade_pay\src\main\resources\xml\spring-rocketmq-producer.xml
 * 中声明为bean
 */
@Setter
public class AceMqProducer {
	public static final Logger logger = LoggerFactory.getLogger(AceMqProducer.class);
	private DefaultMQProducer producer;
    /**
     * spring-rocketmq-producer.xml中声明
     */
    private String groupName;
	private String namesrvAddr;
	private int maxMessageSize = 1024 * 1024 * 4;
	private int sendMsgTimeout = 10000;

	public void init() throws AceMqException {
		if (StringUtils.isBlank(this.groupName)) {
			throw new AceMqException("groupName is blank!");
		}
		if (StringUtils.isBlank(this.namesrvAddr)) {
			throw new AceMqException("namesrvAddr is blank!");
		}
		this.producer = new DefaultMQProducer(this.groupName);
		this.producer.setNamesrvAddr(namesrvAddr);
		this.producer.setMaxMessageSize(maxMessageSize);
		this.producer.setSendMsgTimeout(sendMsgTimeout);
		try {
			this.producer.start();
			logger.info(String.format("producer is start! groupName:{%s}, namesrvAddr:{%s}",
					this.groupName, this.namesrvAddr));
		} catch (MQClientException e) {
			logger.error(String.format("producer error! groupName:{%s}, namesrvAddr:{%s}",
					this.groupName, this.namesrvAddr, e));
			throw new AceMqException(e);
		}
	}

	private SendResult sendMessage(String topic, String tags, String keys, String messageText) throws AceMqException {
		if (StringUtils.isBlank(topic)) {
			throw new AceMqException("topic is blank!");
		}
		if (StringUtils.isBlank(messageText)) {
			throw new AceMqException("messageText is blank!");
		}
		Message message = new Message(topic, tags, keys, messageText.getBytes());
		try {
			SendResult sendResult = this.producer.send(message);

			return sendResult;
		} catch (Exception e) {
			logger.error("send message error:", e.getMessage());
			throw new AceMqException(e);
		}
	}

	public SendResult sendMessage(MqEnums.TopicEnum topicEnum, String keys, String messageText) throws AceMqException {
		return this.sendMessage(topicEnum.getTopic(), topicEnum.getTag(), keys, messageText);
	}
}
