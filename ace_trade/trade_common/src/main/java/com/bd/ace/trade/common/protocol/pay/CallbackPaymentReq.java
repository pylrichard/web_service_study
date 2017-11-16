package com.bd.ace.trade.common.protocol.pay;

public class CallbackPaymentReq {
    private String payId;
    private String isPaid;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }
}
