package com.bd.ace.trade.user.mq.processor;

import com.alibaba.fastjson.JSON;
import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.mq.CancelOrderMq;
import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyReq;
import com.bd.ace.trade.common.rocketmq.IMessageProcessor;
import com.bd.ace.trade.user.service.IUserService;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class CancelOrderProcessor implements IMessageProcessor {
    public static final Logger logger = LoggerFactory.getLogger(CancelOrderProcessor.class);
    @Autowired
    private IUserService userService;

    @Override
    public boolean handleMessage(MessageExt messageExt) {
        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            logger.info("user CancelOrderProcessor receive message:" + body);
            CancelOrderMq cancelOrderMq = JSON.parseObject(body, CancelOrderMq.class);
            if (cancelOrderMq.getUserMoney() != null && cancelOrderMq.getUserMoney().compareTo(BigDecimal.ZERO) == 1) {
                ChangeUserMoneyReq changeUserMoneyReq = new ChangeUserMoneyReq();
                changeUserMoneyReq.setUserId(cancelOrderMq.getUserId());
                changeUserMoneyReq.setMoneyLogType(TradeEnums.UserMoneyLogTypeEnum.REFUND.getCode());
                changeUserMoneyReq.setOrderId(cancelOrderMq.getOrderId());
                changeUserMoneyReq.setUserMoney(cancelOrderMq.getUserMoney());
                userService.changeUserMoney(changeUserMoneyReq);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
