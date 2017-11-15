package com.bd.ace.trade.coupon.service.impl;

import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.coupon.ChangeCouponStatusReq;
import com.bd.ace.trade.common.protocol.coupon.ChangeCouponStatusRes;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponReq;
import com.bd.ace.trade.common.protocol.coupon.QueryCouponRes;
import com.bd.ace.trade.coupon.service.ICouponService;
import com.bd.ace.trade.dao.entity.TradeCoupon;
import com.bd.ace.trade.dao.mapper.TradeCouponMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponServiceImpl implements ICouponService {
    @Autowired
    private TradeCouponMapper tradeCouponMapper;

    @Override
    public QueryCouponRes queryCoupon(QueryCouponReq queryCouponReq) {
        QueryCouponRes queryCouponRes = new QueryCouponRes();
        queryCouponRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
        queryCouponRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        try {
            TradeCoupon tradeCoupon = this.tradeCouponMapper.selectByPrimaryKey(queryCouponReq.getCouponId());
            if (tradeCoupon != null) {
                BeanUtils.copyProperties(tradeCoupon, queryCouponRes);
            } else {
                throw new Exception("未查询到该优惠券");
            }
        } catch (Exception ex) {
            queryCouponRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            queryCouponRes.setRetInfo(ex.getMessage());
        }

        return queryCouponRes;
    }

    @Transactional
    @Override
    public ChangeCouponStatusRes changeCouponStatus(ChangeCouponStatusReq changeCouponStatusReq) {
        ChangeCouponStatusRes changeCouponStatusRes = new ChangeCouponStatusRes();
        changeCouponStatusRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
        changeCouponStatusRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        try {
            TradeCoupon tradeCoupon = new TradeCoupon();
            tradeCoupon.setCouponId(changeCouponStatusReq.getCouponId());
            tradeCoupon.setOrderId(changeCouponStatusReq.getOrderId());
            //创建订单过程中使用优惠券，设置状态为已使用
            if (changeCouponStatusReq.getIsUsed().equals(TradeEnums.YesNoEnum.YES.getCode())) {
                int record = tradeCouponMapper.useCoupon(tradeCoupon);
                if (record <= 0) {
                    throw new Exception("使用优惠券失败");
                }
            }
            //取消订单需要设置优惠券状态为未使用
            else if (changeCouponStatusReq.getIsUsed().equals(TradeEnums.YesNoEnum.NO.getCode())) {
                tradeCouponMapper.unUseCoupon(tradeCoupon);
            }
        } catch (Exception ex) {
            changeCouponStatusRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            changeCouponStatusRes.setRetInfo(ex.getMessage());
        }

        return changeCouponStatusRes;
    }
}
