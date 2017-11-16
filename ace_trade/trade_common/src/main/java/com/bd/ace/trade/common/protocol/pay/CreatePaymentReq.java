package com.bd.ace.trade.common.protocol.pay;

import java.math.BigDecimal;

public class CreatePaymentReq {
    private String orderId;
    private BigDecimal payAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
}
