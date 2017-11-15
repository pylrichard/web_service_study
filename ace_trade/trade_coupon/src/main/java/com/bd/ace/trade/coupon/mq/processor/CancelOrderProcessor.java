package com.bd.ace.trade.coupon.mq.processor;

import com.alibaba.fastjson.JSON;
import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.coupon.ChangeCouponStatusReq;
import com.bd.ace.trade.common.protocol.mq.CancelOrderMq;
import com.bd.ace.trade.common.rocketmq.IMessageProcessor;
import com.bd.ace.trade.coupon.service.ICouponService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CancelOrderProcessor implements IMessageProcessor {
    public static final Logger logger = LoggerFactory.getLogger(CancelOrderProcessor.class);

    @Autowired
    private ICouponService couponService;

    @Override
    public boolean handleMessage(MessageExt messageExt) {
        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            logger.info("coupon CancelOrderProcessor receive message:" + body);
            CancelOrderMq cancelOrderMq = JSON.parseObject(body, CancelOrderMq.class);
            if (StringUtils.isNotBlank(cancelOrderMq.getCouponId())) {
                ChangeCouponStatusReq changeCouponStatusReq = new ChangeCouponStatusReq();
                changeCouponStatusReq.setOrderId(cancelOrderMq.getOrderId());
                changeCouponStatusReq.setCouponId(cancelOrderMq.getCouponId());
                changeCouponStatusReq.setIsUsed(TradeEnums.YesNoEnum.NO.getCode());
                this.couponService.changeCouponStatus(changeCouponStatusReq);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
