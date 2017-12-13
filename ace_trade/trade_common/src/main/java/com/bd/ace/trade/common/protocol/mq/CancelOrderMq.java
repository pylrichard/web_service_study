package com.bd.ace.trade.common.protocol.mq;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CancelOrderMq {
    private String orderId;
    private Integer userId;
    private Integer goodsId;
    private Integer goodsNumber;
    private String couponId;
    private BigDecimal userMoney;
}
