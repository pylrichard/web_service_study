package com.bd.ace.trade.common.api;

import com.bd.ace.trade.common.protocol.coupon.ChangeCoponStatusReq;
import com.bd.ace.trade.common.protocol.coupon.ChangeCoponStatusRes;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponReq;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponRes;

public interface ICouponApi {
    QueryCouponRes queryCoupon(QueryCouponReq queryCouponReq);
    ChangeCoponStatusRes changeCoponStatus(ChangeCoponStatusReq changeCoponStatusReq);
}
