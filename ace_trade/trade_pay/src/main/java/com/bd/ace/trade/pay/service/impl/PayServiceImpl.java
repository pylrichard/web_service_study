package com.bd.ace.trade.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.bd.ace.trade.common.constant.MqEnums;
import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.exception.AceMqException;
import com.bd.ace.trade.common.protocol.mq.PaidMq;
import com.bd.ace.trade.common.protocol.pay.CallbackPaymentReq;
import com.bd.ace.trade.common.protocol.pay.CallbackPaymentRes;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentReq;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentRes;
import com.bd.ace.trade.common.rocketmq.AceMqProducer;
import com.bd.ace.trade.common.util.IdGenerator;
import com.bd.ace.trade.dao.entity.TradeMqProducerLog;
import com.bd.ace.trade.dao.entity.TradePay;
import com.bd.ace.trade.dao.entity.TradePayExample;
import com.bd.ace.trade.dao.mapper.TradeMqProducerLogMapper;
import com.bd.ace.trade.dao.mapper.TradePayMapper;
import com.bd.ace.trade.pay.service.IPayService;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PayServiceImpl implements IPayService {
    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private TradePayMapper tradePayMapper;
    @Autowired
    private AceMqProducer aceMqProducer;
    @Autowired
    private TradeMqProducerLogMapper tradeMqProducerLogMapper;
    /**
     * 使用线程池异步发送消息
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public CreatePaymentRes createPayment(CreatePaymentReq createPaymentReq) {
        CreatePaymentRes createPaymentRes = new CreatePaymentRes();
        createPaymentRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
        createPaymentRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        try {
            TradePayExample payExample = new TradePayExample();
            payExample.createCriteria()
                    .andOrderIdEqualTo(createPaymentReq.getOrderId())
                    .andIsPaidEqualTo(TradeEnums.YesNoEnum.YES.getCode());
            int count = this.tradePayMapper.countByExample(payExample);
            if (count > 0) {
                throw new Exception("该订单已支付");
            }
            String payId = IdGenerator.generateId();
            TradePay tradePay = new TradePay();
            tradePay.setPayId(payId);
            tradePay.setOrderId(createPaymentReq.getOrderId());
            tradePay.setIsPaid(TradeEnums.YesNoEnum.NO.getCode());
            tradePay.setPayAmount(createPaymentReq.getPayAmount());
            tradePayMapper.insert(tradePay);
            logger.info("创建支付订单成功:" + payId);
        } catch (Exception e) {
            createPaymentRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            createPaymentRes.setRetInfo(e.getMessage());
        }

        return createPaymentRes;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CallbackPaymentRes callbackPayment(CallbackPaymentReq callbackPaymentReq) {
        CallbackPaymentRes callbackPaymentRes = new CallbackPaymentRes();
        callbackPaymentRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
        callbackPaymentRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        if (callbackPaymentReq.getIsPaid().equals(TradeEnums.YesNoEnum.YES.getCode())) {
            /*
                更新支付表的记录状态
             */
            TradePay tradePay = tradePayMapper.selectByPrimaryKey(callbackPaymentReq.getPayId());
            if (tradePay == null) {
                throw new RuntimeException("未找到支付订单");
            }
            if (tradePay.getIsPaid().equals(TradeEnums.YesNoEnum.YES.getCode())) {
                throw new RuntimeException("该支付单已支付");
            }
            tradePay.setIsPaid(TradeEnums.YesNoEnum.YES.getCode());
            int i = tradePayMapper.updateByPrimaryKeySelective(tradePay);
            //更新行数为1表示更新成功
            if (i == 1) {
                /*
                    发送消息通知订单服务订单支付成功，更新订单表记录状态
                    RocketMQ 4.0之前的版本不支持分布式事务，此处转换为本地事务
                    更新支付表记录状态和生产者消息日志表插入记录在一个事务中
                 */
                final PaidMq paidMq = new PaidMq();
                paidMq.setPayAmount(tradePay.getPayAmount());
                paidMq.setOrderId(tradePay.getOrderId());
                paidMq.setPayId(tradePay.getPayId());
                /*
                    生产者消息日志表插入记录表示有一条待发送消息
                 */
                final TradeMqProducerLog mqProducerLog = new TradeMqProducerLog();
                mqProducerLog.setId(IdGenerator.generateId());
                mqProducerLog.setGroupName("PayProducerGroup");
                mqProducerLog.setMsgKeys(tradePay.getPayId());
                mqProducerLog.setMsgTag(MqEnums.TopicEnum.PAY_PAID.getTag());
                mqProducerLog.setMsgBody(JSON.toJSONString(paidMq));
                mqProducerLog.setCreateTime(new Date());
                tradeMqProducerLogMapper.insert(mqProducerLog);
                /*
                    异步发送消息到MQ，发送成功后删除消息日志表的相应记录
                    后台任务定时扫描消息日志表，根据消息创建时间，将超过时间阈值没有发送成功的消息再次发送
                 */
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SendResult sendResult = aceMqProducer.sendMessage(MqEnums.TopicEnum.PAY_PAID,
                                                                            paidMq.getPayId(),
                                                                            JSON.toJSONString(paidMq));
                            logger.info("支付成功发送消息:" + sendResult);
                            if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                                tradeMqProducerLogMapper.deleteByPrimaryKey(mqProducerLog.getId());
                                logger.info("删除消息日志表记录成功");
                            }
                        } catch (AceMqException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                throw new RuntimeException("该支付单已支付");
            }
        }

        return callbackPaymentRes;
    }
}
