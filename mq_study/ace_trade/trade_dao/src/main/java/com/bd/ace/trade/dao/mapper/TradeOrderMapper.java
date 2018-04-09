package com.bd.ace.trade.dao.mapper;

import com.bd.ace.trade.dao.entity.TradeOrder;
import com.bd.ace.trade.dao.entity.TradeOrderExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TradeOrderMapper {
    int countByExample(TradeOrderExample example);

    int deleteByExample(TradeOrderExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(TradeOrder record);

    int insertSelective(TradeOrder record);

    List<TradeOrder> selectByExample(TradeOrderExample example);

    TradeOrder selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") TradeOrder record, @Param("example") TradeOrderExample example);

    int updateByExample(@Param("record") TradeOrder record, @Param("example") TradeOrderExample example);

    int updateByPrimaryKeySelective(TradeOrder record);

    int updateByPrimaryKey(TradeOrder record);
}