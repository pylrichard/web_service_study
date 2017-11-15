package com.bd.ace.trade.coupon.api;

import com.bd.ace.trade.common.api.ICouponApi;
import com.bd.ace.trade.common.protocol.coupon.ChangeCouponStatusReq;
import com.bd.ace.trade.common.protocol.coupon.ChangeCouponStatusRes;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponReq;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponRes;
import com.bd.ace.trade.coupon.service.ICouponService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponApiImpl implements ICouponApi{
	@Autowired
	private ICouponService couponService;
     
	@Override
	@PostMapping("/queryCoupon")
	public QueryCouponRes queryCoupon(@RequestBody QueryCouponReq queryCouponReq) {
		if (queryCouponReq == null || StringUtils.isBlank(queryCouponReq.getCouponId())) {
			//返回给前端的响应包含错误信息
			throw new RuntimeException("请求参数不正确，优惠券编号为空");
		}

		return couponService.queryCoupon(queryCouponReq);
	}

	@Override
	public ChangeCouponStatusRes changeCouponStatus(@RequestBody ChangeCouponStatusReq changeCouponStatusReq)
			throws Exception {
		if (changeCouponStatusReq == null || StringUtils.isBlank(changeCouponStatusReq.getCouponId())) {
			throw new Exception("请求参数不正确，优惠券编号为空");
		}

		return couponService.changeCouponStatus(changeCouponStatusReq);
	}
}
