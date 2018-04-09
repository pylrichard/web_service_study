package com.bd.ace.trade.dao.mapper;

import com.bd.ace.trade.dao.entity.TradePay;
import com.bd.ace.trade.dao.entity.TradePayExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TradePayMapper {
    int countByExample(TradePayExample example);

    int deleteByExample(TradePayExample example);

    int deleteByPrimaryKey(String payId);

    int insert(TradePay record);

    int insertSelective(TradePay record);

    List<TradePay> selectByExample(TradePayExample example);

    TradePay selectByPrimaryKey(String payId);

    int updateByExampleSelective(@Param("record") TradePay record, @Param("example") TradePayExample example);

    int updateByExample(@Param("record") TradePay record, @Param("example") TradePayExample example);

    int updateByPrimaryKeySelective(TradePay record);

    int updateByPrimaryKey(TradePay record);
}