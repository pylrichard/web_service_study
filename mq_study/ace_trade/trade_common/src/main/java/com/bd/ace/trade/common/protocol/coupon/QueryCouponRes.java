package com.bd.ace.trade.common.protocol.coupon;

import com.bd.ace.trade.common.protocol.BaseRes;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class QueryCouponRes extends BaseRes {
    private String couponId;
    private BigDecimal couponPrice;
    private Integer userId;
    private String orderId;
    private String isUsed;
    private Date usedTime;
}
