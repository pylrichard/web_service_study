package com.bd.ace.trade.common.protocol.pay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallbackPaymentReq {
    private String payId;
    private String isPaid;
}
