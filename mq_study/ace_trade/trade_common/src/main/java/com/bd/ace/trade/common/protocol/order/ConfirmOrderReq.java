package com.bd.ace.trade.common.protocol.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConfirmOrderReq {
    private Integer userId;
    private String address;
    private String consignee;
    private Integer goodsId;
    private Integer goodsNumber;
    /**
     * 优惠券id
     */
    private String couponId;
    /**
     * 余额支付
     */
    private BigDecimal moneyPaid;
    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;
    /**
     * 订单总价
     */
    private BigDecimal orderAmount;
    /**
     * 运费
     */
    private BigDecimal shippingFee;
}
