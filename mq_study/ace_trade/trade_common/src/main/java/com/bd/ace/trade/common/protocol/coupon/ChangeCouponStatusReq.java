package com.bd.ace.trade.common.protocol.coupon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeCouponStatusReq {
    private String couponId;
    private String orderId;
    private String isUsed;
}
