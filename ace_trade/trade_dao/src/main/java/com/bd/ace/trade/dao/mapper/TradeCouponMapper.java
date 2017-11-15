package com.bd.ace.trade.dao.mapper;

import com.bd.ace.trade.dao.entity.TradeCoupon;
import com.bd.ace.trade.dao.entity.TradeCouponExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeCouponMapper {
    int countByExample(TradeCouponExample example);

    int deleteByExample(TradeCouponExample example);

    int deleteByPrimaryKey(String couponId);

    int insert(TradeCoupon record);

    int insertSelective(TradeCoupon record);

    List<TradeCoupon> selectByExample(TradeCouponExample example);

    TradeCoupon selectByPrimaryKey(String couponId);

    int updateByExampleSelective(@Param("record") TradeCoupon record, @Param("example") TradeCouponExample example);

    int updateByExample(@Param("record") TradeCoupon record, @Param("example") TradeCouponExample example);

    int updateByPrimaryKeySelective(TradeCoupon record);

    int updateByPrimaryKey(TradeCoupon record);

    int useCoupon(TradeCoupon record);

    int unUseCoupon(TradeCoupon record);
}