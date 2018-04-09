package com.bd.ace.trade.common.protocol.mq;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaidMq {
    private String payId;
    private String orderId;
    private BigDecimal payAmount;
}
