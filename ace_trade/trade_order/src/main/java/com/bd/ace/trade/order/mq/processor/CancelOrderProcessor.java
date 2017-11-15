package com.bd.ace.trade.order.mq.processor;

import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.mq.CancelOrderMq;
import com.bd.ace.trade.common.protocol.order.ChangeOrderStatusReq;
import com.bd.ace.trade.common.rocketmq.IMessageProcessor;
import com.bd.ace.trade.order.service.IOrderService;
import com.alibaba.fastjson.JSON;

public class CancelOrderProcessor implements IMessageProcessor {
    public static final Logger logger = LoggerFactory.getLogger(CancelOrderProcessor.class);

    @Autowired
    private IOrderService orderService;

    @Override
    public boolean handleMessage(MessageExt messageExt) {
        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            logger.info("order CancelOrderProcessor receive message:" + body);
            CancelOrderMq cancelOrderMq = JSON.parseObject(body, CancelOrderMq.class);
            ChangeOrderStatusReq changeOrderStatusReq = new ChangeOrderStatusReq();
            changeOrderStatusReq.setOrderId(cancelOrderMq.getOrderId());
            changeOrderStatusReq.setOrderStatus(TradeEnums.OrderStatusEnum.CANCEL.getStatusCode());
            orderService.changeOrderStatus(changeOrderStatusReq);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
