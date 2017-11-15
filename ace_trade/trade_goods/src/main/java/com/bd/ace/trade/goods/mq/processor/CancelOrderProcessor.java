package com.bd.ace.trade.goods.mq.processor;

import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bd.ace.trade.common.constant.MqEnums;
import com.bd.ace.trade.common.protocol.goods.AddGoodsNumberReq;
import com.bd.ace.trade.common.protocol.mq.CancelOrderMq;
import com.bd.ace.trade.common.rocketmq.IMessageProcessor;
import com.bd.ace.trade.dao.entity.TradeMqConsumerLog;
import com.bd.ace.trade.dao.entity.TradeMqConsumerLogExample;
import com.bd.ace.trade.dao.entity.TradeMqConsumerLogKey;
import com.bd.ace.trade.goods.service.IGoodsService;
import com.bd.ace.trade.dao.mapper.TradeMqConsumerLogMapper;
import com.alibaba.fastjson.JSON;

public class CancelOrderProcessor implements IMessageProcessor {
    public static final Logger logger = LoggerFactory.getLogger(CancelOrderProcessor.class);

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private TradeMqConsumerLogMapper tradeMqConsumerLogMapper;

    @Override
    public boolean handleMessage(MessageExt messageExt) {
        TradeMqConsumerLog mqConsumerLog = null;
        TradeMqConsumerLog updateMqConsumerLog = new TradeMqConsumerLog();
        try {
            String groupName = "GoodsOrderTopicCancelGroup";
            String body = new String(messageExt.getBody(), "UTF-8");
            String msgId = messageExt.getMsgId();
            String tags = messageExt.getTags();
            String keys = messageExt.getKeys();
            logger.info("goods CancelOrderProcessor receive message:" + body);

            TradeMqConsumerLogKey key = new TradeMqConsumerLogKey();
            key.setGroupName(groupName);
            key.setMsgKey(keys);
            key.setMsgTag(tags);

            mqConsumerLog = this.tradeMqConsumerLogMapper.selectByPrimaryKey(key);
            //消息已存在于MQ消费者日志表
            if (mqConsumerLog != null) {
                String consumerStatus = mqConsumerLog.getConsumerStatus();
                if (MqEnums.ConsumerStatusEnum.SUCCESS.getStatusCode().equals(consumerStatus)) {
                    logger.warn("已经处理过了，不能再处理");

                    return true;
                } else if (MqEnums.ConsumerStatusEnum.PROCESSING.getStatusCode().equals(consumerStatus)) {
                    logger.warn("有消费者正在处理，稍后再试");

                    return false;
                } else if (MqEnums.ConsumerStatusEnum.FAIL.getStatusCode().equals(consumerStatus)) {
                    if (mqConsumerLog.getConsumerTimes() >= 3) {
                        //这条消息不再重试，进行人工处理
                        logger.warn("超过重试次数，不再处理");

                        return true;
                    }
                    /*
                        更新消息状态为处理中
                     */
                    updateMqConsumerLog.setGroupName(groupName);
                    updateMqConsumerLog.setMsgKey(keys);
                    updateMqConsumerLog.setMsgTag(tags);
                    updateMqConsumerLog.setConsumerStatus(MqEnums.ConsumerStatusEnum.PROCESSING.getStatusCode());
                    /*
                        用乐观锁防止并发更新
                     */
                    TradeMqConsumerLogExample tradeMqConsumerLogExample = new TradeMqConsumerLogExample();
                    tradeMqConsumerLogExample.createCriteria()
                            .andGroupNameEqualTo(mqConsumerLog.getGroupName())
                            .andMsgKeyEqualTo(mqConsumerLog.getMsgKey())
                            .andMsgTagEqualTo(mqConsumerLog.getMsgTag())
                            //与当前处理次数相同才进行更新
                            .andConsumerTimesEqualTo(mqConsumerLog.getConsumerTimes());
                    int record = this.tradeMqConsumerLogMapper.updateByExampleSelective(updateMqConsumerLog,
                                                                                        tradeMqConsumerLogExample);
                    if (record <= 0) {
                        logger.warn("并发更新处理状态，稍后重试");

                        return false;
                    }
                }
            }
            //消息第一次插入MQ消费者日志表
            else {
                try {
                    TradeMqConsumerLog tradeMqConsumerLog = new TradeMqConsumerLog();
                    tradeMqConsumerLog.setGroupName(groupName);
                    tradeMqConsumerLog.setMsgKey(keys);
                    tradeMqConsumerLog.setMsgTag(tags);
                    tradeMqConsumerLog.setConsumerTimes(0);
                    tradeMqConsumerLog.setMsgId(msgId);
                    tradeMqConsumerLog.setMsgBody(body);
                    tradeMqConsumerLog.setConsumerStatus(MqEnums.ConsumerStatusEnum.PROCESSING.getStatusCode());
                    this.tradeMqConsumerLogMapper.insertSelective(tradeMqConsumerLog);
                } catch (Exception e) {
                    //并发时用主键冲突控制多个订阅者同时处理同一条消息
                    logger.warn("主键冲突，说明有订阅者正在处理，稍后再试");

                    return false;
                }
            }

            /*
                添加商品库存
             */
            CancelOrderMq cancelOrderMq = JSON.parseObject(body, CancelOrderMq.class);
            AddGoodsNumberReq addGoodsNumberReq = new AddGoodsNumberReq();
            addGoodsNumberReq.setGoodsId(cancelOrderMq.getGoodsId());
            addGoodsNumberReq.setGoodsNumber(cancelOrderMq.getGoodsNumber());
            addGoodsNumberReq.setOrderId(cancelOrderMq.getOrderId());
            goodsService.addGoodsNumber(addGoodsNumberReq);

            /*
                更新消息处理成功
             */
            updateMqConsumerLog.setGroupName(groupName);
            updateMqConsumerLog.setMsgKey(keys);
            updateMqConsumerLog.setMsgTag(tags);
            updateMqConsumerLog.setConsumerTimes(mqConsumerLog.getConsumerTimes() + 1);
            updateMqConsumerLog.setMsgId(msgId);
            updateMqConsumerLog.setMsgBody(body);
            updateMqConsumerLog.setConsumerStatus(MqEnums.ConsumerStatusEnum.SUCCESS.getStatusCode());
            this.tradeMqConsumerLogMapper.updateByPrimaryKeySelective(updateMqConsumerLog);

            return true;
        } catch (Exception e) {
            /*
                更新消息处理失败
             */
            updateMqConsumerLog.setGroupName(mqConsumerLog.getGroupName());
            updateMqConsumerLog.setMsgKey(mqConsumerLog.getMsgKey());
            updateMqConsumerLog.setMsgTag(mqConsumerLog.getMsgTag());
            updateMqConsumerLog.setConsumerTimes(mqConsumerLog.getConsumerTimes() + 1);
            updateMqConsumerLog.setConsumerStatus(MqEnums.ConsumerStatusEnum.FAIL.getStatusCode());
            this.tradeMqConsumerLogMapper.updateByPrimaryKeySelective(updateMqConsumerLog);

            return false;
        }
    }
}
