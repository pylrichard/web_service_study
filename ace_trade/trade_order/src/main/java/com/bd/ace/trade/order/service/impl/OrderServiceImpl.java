package com.bd.ace.trade.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.bd.ace.trade.common.api.ICouponApi;
import com.bd.ace.trade.common.api.IGoodsApi;
import com.bd.ace.trade.common.api.IUserApi;
import com.bd.ace.trade.common.constant.MqEnums;
import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.exception.AceMqException;
import com.bd.ace.trade.common.exception.AceOrderException;
import com.bd.ace.trade.common.protocol.coupon.ChangeCouponStatusReq;
import com.bd.ace.trade.common.protocol.coupon.ChangeCouponStatusRes;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponReq;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponRes;
import com.bd.ace.trade.common.protocol.goods.QueryGoodsReq;
import com.bd.ace.trade.common.protocol.goods.QueryGoodsRes;
import com.bd.ace.trade.common.protocol.goods.ReduceGoodsNumberReq;
import com.bd.ace.trade.common.protocol.goods.ReduceGoodsNumberRes;
import com.bd.ace.trade.common.protocol.mq.CancelOrderMq;
import com.bd.ace.trade.common.protocol.order.ChangeOrderStatusReq;
import com.bd.ace.trade.common.protocol.order.ChangeOrderStatusRes;
import com.bd.ace.trade.common.protocol.order.ConfirmOrderReq;
import com.bd.ace.trade.common.protocol.order.ConfirmOrderRes;
import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyReq;
import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyRes;
import com.bd.ace.trade.common.protocol.user.QueryUserReq;
import com.bd.ace.trade.common.protocol.user.QueryUserRes;
import com.bd.ace.trade.common.rocketmq.AceMqProducer;
import com.bd.ace.trade.common.util.IdGenerator;
import com.bd.ace.trade.dao.entity.TradeOrder;
import com.bd.ace.trade.dao.mapper.TradeOrderMapper;
import com.bd.ace.trade.order.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IGoodsApi goodsApi;
    @Autowired
    private TradeOrderMapper tradeOrderMapper;
    @Autowired
    private ICouponApi couponApi;
    @Autowired
    private IUserApi userApi;
    @Autowired
    private AceMqProducer aceMqProducer;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ConfirmOrderRes confirmOrder(ConfirmOrderReq confirmOrderReq) {
        ConfirmOrderRes confirmOrderRes = new ConfirmOrderRes();
        try {
            QueryGoodsReq queryGoodsReq = new QueryGoodsReq();
            queryGoodsReq.setGoodsId(confirmOrderReq.getGoodsId());
            //查询商品信息
            QueryGoodsRes queryGoodsRes = goodsApi.queryGoods(queryGoodsReq);
            //验证订单请求和商品信息
            checkConfirmOrderReq(confirmOrderReq, queryGoodsRes);
            //创建不可见订单
            String orderId = createNoConfirmOrder(confirmOrderReq);
            //调用远程服务，扣优惠券和库存，如果调用成功，更改订单状态为可见，失败则发送消息到MQ，取消订单
            callRemoteService(confirmOrderReq, orderId);
            confirmOrderRes.setOrderId(orderId);
        } catch (Exception e) {
            confirmOrderRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            confirmOrderRes.setRetInfo(e.getMessage());
        }

        return confirmOrderRes;
    }

    private void callRemoteService(ConfirmOrderReq confirmOrderReq, String orderId) {
        try {
            /*
                调用优惠券服务，使用优惠券
             */
            if (StringUtils.isNotBlank(confirmOrderReq.getCouponId())) {
                ChangeCouponStatusReq changeCouponStatusReq = new ChangeCouponStatusReq();
                changeCouponStatusReq.setCouponId(confirmOrderReq.getCouponId());
                changeCouponStatusReq.setIsUsed(TradeEnums.YesNoEnum.YES.getCode());
                changeCouponStatusReq.setOrderId(orderId);
                //修改优惠券状态为已使用
                ChangeCouponStatusRes changeCouponStatusRes = couponApi.changeCouponStatus(changeCouponStatusReq);
                if (!changeCouponStatusRes.getRetCode().equals(TradeEnums.RetEnum.SUCCESS)) {
                    throw new Exception("优惠券使用失败");
                }
            }
            /*
                调用用户服务，扣余额
             */
            if (confirmOrderReq.getMoneyPaid() != null
                    && confirmOrderReq.getMoneyPaid().compareTo(BigDecimal.ZERO) == 1) {
                ChangeUserMoneyReq changeUserMoneyReq = new ChangeUserMoneyReq();
                changeUserMoneyReq.setOrderId(orderId);
                changeUserMoneyReq.setUserMoney(confirmOrderReq.getMoneyPaid());
                changeUserMoneyReq.setMoneyLogType(TradeEnums.UserMoneyLogTypeEnum.PAID.getCode());
                //修改用户余额
                ChangeUserMoneyRes changeUserMoneyRes = userApi.changeUserMoney(changeUserMoneyReq);
                if (!changeUserMoneyRes.getRetCode().equals(TradeEnums.RetEnum.SUCCESS)) {
                    throw new Exception("扣用户余额失败");
                }
            }
            /*
                调用商品库存服务，扣库存
             */
            ReduceGoodsNumberReq reduceGoodsNumberReq = new ReduceGoodsNumberReq();
            reduceGoodsNumberReq.setOrderId(orderId);
            reduceGoodsNumberReq.setGoodsId(confirmOrderReq.getGoodsId());
            reduceGoodsNumberReq.setGoodsNumber(confirmOrderReq.getGoodsNumber());
            //修改商品库存
            ReduceGoodsNumberRes reduceGoodsNumberRes = goodsApi.reduceGoodsNumber(reduceGoodsNumberReq);
            if (!reduceGoodsNumberRes.getRetCode().equals(TradeEnums.RetEnum.SUCCESS)) {
                throw new Exception("扣库存失败");
            }
            /*
                TODO 调用支付服务，支付订单需支付金额
             */
            /*
                以上服务调用都成功则修改订单状态
             */
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setOrderId(orderId);
            tradeOrder.setOrderStatus(TradeEnums.OrderStatusEnum.CONFIRM.getStatusCode());
            tradeOrder.setConfirmTime(new Date());
            int row = tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder);
            if (row <= 0) {
                throw new Exception("修改订单状态失败");
            }
        } catch (Exception ex) {
            /*
                触发异常则发送消息到MQ，取消订单
             */
            CancelOrderMq cancelOrderMq = new CancelOrderMq();
            cancelOrderMq.setOrderId(orderId);
            cancelOrderMq.setCouponId(confirmOrderReq.getCouponId());
            cancelOrderMq.setGoodsId(confirmOrderReq.getGoodsId());
            cancelOrderMq.setGoodsNumber(confirmOrderReq.getGoodsNumber());
            cancelOrderMq.setUserId(confirmOrderReq.getUserId());
            cancelOrderMq.setUserMoney(confirmOrderReq.getMoneyPaid());
            try {
                aceMqProducer.sendMessage(MqEnums.TopicEnum.ORDER_CANCEL, orderId, JSON.toJSONString(cancelOrderMq));
            } catch (AceMqException e) {}
            throw new RuntimeException(ex.getMessage());
        }
    }

    private String createNoConfirmOrder(ConfirmOrderReq confirmOrderReq) throws Exception {
        TradeOrder tradeOrder = new TradeOrder();
        //生成全局唯一订单号
        tradeOrder.setOrderId(IdGenerator.generateId());
        tradeOrder.setUserId(confirmOrderReq.getUserId());
        tradeOrder.setOrderStatus(TradeEnums.OrderStatusEnum.NO_CONFIRM.getStatusCode());
        tradeOrder.setPayStatus(TradeEnums.PayStatusEnum.NO_PAY.getStatusCode());
        tradeOrder.setShippingStatus(TradeEnums.ShippingStatusEnum.NO_SHIP.getStatusCode());
        tradeOrder.setAddress(confirmOrderReq.getAddress());
        tradeOrder.setConsignee(confirmOrderReq.getConsignee());
        tradeOrder.setGoodsId(confirmOrderReq.getGoodsId());
        tradeOrder.setGoodsNumber(confirmOrderReq.getGoodsNumber());
        tradeOrder.setGoodsPrice(confirmOrderReq.getGoodsPrice());
        //计算商品总价
        BigDecimal goodsAmount = confirmOrderReq.getGoodsPrice()
                .multiply(new BigDecimal(confirmOrderReq.getGoodsNumber()));
        tradeOrder.setGoodsAmount(goodsAmount);
        //计算运费
        BigDecimal shippingFee = calculateShippingFee(goodsAmount);
        if (confirmOrderReq.getShippingFee().compareTo(shippingFee) != 0) {
            throw new Exception("快递费用不正确");
        }
        tradeOrder.setShippingFee(shippingFee);
        //得到订单总价
        BigDecimal orderAmount = goodsAmount.add(shippingFee);
        if (orderAmount.compareTo(confirmOrderReq.getOrderAmount()) != 0) {
            throw new Exception("订单总价不正确，请重新下单!");
        }
        tradeOrder.setOrderAmount(orderAmount);
        /*
            使用优惠券
         */
        String couponId = confirmOrderReq.getCouponId();
        if (couponId != null) {
            QueryCouponReq queryCouponReq = new QueryCouponReq();
            queryCouponReq.setCouponId(couponId);
            //查询优惠券信息
            QueryCouponRes queryCouponRes = couponApi.queryCoupon(queryCouponReq);
            if (queryCouponRes == null || !queryCouponRes.getRetCode().equals(TradeEnums.RetEnum.SUCCESS.getCode())) {
                throw new Exception("优惠券无效");
            }
            if (queryCouponRes.getIsUsed().equals(TradeEnums.YesNoEnum.NO.getCode())) {
                throw new Exception("优惠券已使用");
            }
            tradeOrder.setCouponId(couponId);
            tradeOrder.setCouponPaid(queryCouponRes.getCouponPrice());
        } else {
            tradeOrder.setCouponPaid(BigDecimal.ZERO);
        }
        /*
            使用余额支付
         */
        if (confirmOrderReq.getMoneyPaid() != null) {
            //验证要使用余额
            int result = confirmOrderReq.getMoneyPaid().compareTo(BigDecimal.ZERO);
            if (-1 == result) {
                throw new Exception("余额无效");
            }
            if (1 == result) {
                QueryUserReq queryUserReq = new QueryUserReq();
                queryUserReq.setUserId(confirmOrderReq.getUserId());
                //查询用户余额
                QueryUserRes queryUserRes = userApi.queryUser(queryUserReq);
                if (queryUserRes == null || !queryUserRes.getRetCode().equals(TradeEnums.RetEnum.SUCCESS.getCode())) {
                    throw new Exception("用户非法");
                }
                //验证用户余额是否满足使用
                if (queryUserRes.getUserMoney().compareTo(confirmOrderReq.getMoneyPaid()) == -1) {
                    throw new Exception("余额不足");
                }
                tradeOrder.setMoneyPaid(confirmOrderReq.getMoneyPaid());
            }
        } else {
            tradeOrder.setMoneyPaid(BigDecimal.ZERO);
        }
        //计算订单需支付金额
        BigDecimal payAmount = orderAmount.subtract(tradeOrder.getMoneyPaid().subtract(tradeOrder.getCouponPaid()));
        tradeOrder.setPayAmount(payAmount);
        tradeOrder.setCreateTime(new Date());
        int ret = this.tradeOrderMapper.insert(tradeOrder);
        if (ret != 1) {
            throw new Exception("创建订单失败");
        }

        return tradeOrder.getOrderId();
    }

    private BigDecimal calculateShippingFee(BigDecimal goodsAmount) {
        //满100免邮
        if (goodsAmount.doubleValue() > 100.00) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(10);
        }
    }

    private void checkConfirmOrderReq(ConfirmOrderReq confirmOrderReq, QueryGoodsRes queryGoodsRes) {
        if (confirmOrderReq == null) {
            throw new AceOrderException("下单信息不能为空");
        }
        if (confirmOrderReq.getUserId() == null) {
            throw new AceOrderException("会员账号不能为空");
        }
        if (confirmOrderReq.getGoodsId() == null) {
            throw new AceOrderException("商品编号不能为空");
        }
        if (confirmOrderReq.getGoodsNumber() == null || confirmOrderReq.getGoodsNumber() <= 0) {
            throw new AceOrderException("购买数量不能小于0");
        }
        if (confirmOrderReq.getAddress() == null) {
            throw new AceOrderException("商品地址不能为空");
        }
        if (queryGoodsRes == null || queryGoodsRes.getRetCode().equals(TradeEnums.RetEnum.SUCCESS)) {
            throw new AceOrderException("未查询到该商品[" + confirmOrderReq.getGoodsId() + "]");
        }
        if (queryGoodsRes.getGoodsNumber() < confirmOrderReq.getGoodsNumber()) {
            throw new AceOrderException("商品库存不足");
        }
        if (queryGoodsRes.getGoodsPrice().compareTo(confirmOrderReq.getGoodsPrice()) != 0) {
            throw new AceOrderException("当前商品价格有变化，请重新下单");
        }
        if (confirmOrderReq.getShippingFee() == null) {
            confirmOrderReq.setShippingFee(BigDecimal.ZERO);
        }
        if (confirmOrderReq.getOrderAmount() == null) {
            confirmOrderReq.setOrderAmount(BigDecimal.ZERO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ChangeOrderStatusRes changeOrderStatus(ChangeOrderStatusReq changeOrderStatusReq) {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setOrderId(changeOrderStatusReq.getOrderId());
        tradeOrder.setOrderStatus(changeOrderStatusReq.getOrderStatus());
        ChangeOrderStatusRes changeOrderStatusRes = new ChangeOrderStatusRes();
        try {
            int num = tradeOrderMapper.updateByPrimaryKey(tradeOrder);
            if (num <= 0) {
                throw new Exception("订单状态修改异常");
            }
            changeOrderStatusRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
            changeOrderStatusRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            changeOrderStatusRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            changeOrderStatusRes.setRetInfo(e.getMessage());
        }

        return changeOrderStatusRes;
    }
}
