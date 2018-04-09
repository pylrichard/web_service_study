package com.bd.ace.trade.web;

import com.alibaba.fastjson.JSON;
import com.bd.ace.trade.common.api.*;
import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponReq;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponRes;
import com.bd.ace.trade.common.protocol.goods.QueryGoodsReq;
import com.bd.ace.trade.common.protocol.goods.QueryGoodsRes;
import com.bd.ace.trade.common.protocol.order.ConfirmOrderReq;
import com.bd.ace.trade.common.protocol.order.ConfirmOrderRes;
import com.bd.ace.trade.common.protocol.pay.CallbackPaymentReq;
import com.bd.ace.trade.common.protocol.pay.CallbackPaymentRes;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentReq;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentRes;
import com.bd.ace.trade.common.protocol.user.QueryUserReq;
import com.bd.ace.trade.common.protocol.user.QueryUserRes;
import com.bd.ace.trade.common.util.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@ContextConfiguration(locations = {"classpath:xml/spring-rest-client.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AceTradeTest {
    @Autowired
    private IGoodsApi goodsApi;
    @Autowired
    private ICouponApi couponApi;
    @Autowired
    private IUserApi userApi;
    @Autowired
    private IOrderApi orderApi;
    @Autowired
    private IPayApi payApi;

    @Test
    public void testUser() {
        QueryUserReq queryUserReq = new QueryUserReq();
        queryUserReq.setUserId(1);
        QueryUserRes queryUserRes = userApi.queryUser(queryUserReq);
        System.out.println(JSON.toJSON(queryUserRes));
    }

    @Test
    public void testGoods() {
        QueryGoodsReq queryGoodsReq = new QueryGoodsReq();
        queryGoodsReq.setGoodsId(10000);
        QueryGoodsRes queryGoodsRes = goodsApi.queryGoods(queryGoodsReq);
        System.out.println(JSON.toJSON(queryGoodsRes));
    }

    @Test
    public void testCoupon() {
        QueryCouponReq queryCouponReq = new QueryCouponReq();
        queryCouponReq.setCouponId("123456789");
        QueryCouponRes queryCouponRes = null;
        queryCouponRes = couponApi.queryCoupon(queryCouponReq);
        System.out.println(JSON.toJSON(queryCouponRes));
    }

    @Test
    public void testConfimOrder() {
        ConfirmOrderReq confirmOrderReq = new ConfirmOrderReq();
        confirmOrderReq.setGoodsId(10000);
        confirmOrderReq.setUserId(1);
        confirmOrderReq.setGoodsNumber(1);
        confirmOrderReq.setAddress("北京");
        confirmOrderReq.setGoodsPrice(new BigDecimal("5000"));
        confirmOrderReq.setOrderAmount(new BigDecimal("5000"));
        confirmOrderReq.setMoneyPaid(new BigDecimal("100"));
        confirmOrderReq.setCouponId("123456789");
        ConfirmOrderRes confirmOrderRes = orderApi.confirmOrder(confirmOrderReq);
        System.out.println(JSON.toJSON(confirmOrderRes));
    }

    @Test
    public void testCreatePayment() {
        CreatePaymentReq createPaymentReq = new CreatePaymentReq();
        createPaymentReq.setPayAmount(new BigDecimal("3000"));
        createPaymentReq.setOrderId(IdGenerator.generateId());
        CreatePaymentRes createPaymentRes = payApi.createPayment(createPaymentReq);
        System.out.println(JSON.toJSON(createPaymentRes));
    }

    @Test
    public void testCallbackPayment() {
        CallbackPaymentReq callbackPaymentReq = new CallbackPaymentReq();
        callbackPaymentReq.setPayId(IdGenerator.generateId());
        callbackPaymentReq.setIsPaid(TradeEnums.YesNoEnum.YES.getCode());
        CallbackPaymentRes callbackPaymentRes = payApi.callbackPayment(callbackPaymentReq);
        System.out.println(JSON.toJSON(callbackPaymentRes));
    }
}
