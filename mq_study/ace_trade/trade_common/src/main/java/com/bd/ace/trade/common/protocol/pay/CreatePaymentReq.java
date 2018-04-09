package com.bd.ace.trade.common.protocol.pay;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreatePaymentReq {
    private String orderId;
    private BigDecimal payAmount;
}
