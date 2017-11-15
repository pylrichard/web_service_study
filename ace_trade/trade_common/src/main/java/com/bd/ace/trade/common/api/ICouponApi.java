package com.bd.ace.trade.common.api;

import com.bd.ace.trade.common.protocol.coupon.ChangeCouponStatusReq;
import com.bd.ace.trade.common.protocol.coupon.ChangeCouponStatusRes;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponReq;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponRes;

public interface ICouponApi {
    QueryCouponRes queryCoupon(QueryCouponReq queryCouponReq);
    ChangeCouponStatusRes changeCouponStatus(ChangeCouponStatusReq changeCouponStatusReq) throws Exception;
}
