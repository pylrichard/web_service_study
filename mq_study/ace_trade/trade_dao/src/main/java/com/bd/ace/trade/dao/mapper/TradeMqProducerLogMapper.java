package com.bd.ace.trade.dao.mapper;

import com.bd.ace.trade.dao.entity.TradeMqProducerLog;
import com.bd.ace.trade.dao.entity.TradeMqProducerLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TradeMqProducerLogMapper {
    long countByExample(TradeMqProducerLogExample example);

    int deleteByExample(TradeMqProducerLogExample example);

    int deleteByPrimaryKey(String id);

    int insert(TradeMqProducerLog record);

    int insertSelective(TradeMqProducerLog record);

    List<TradeMqProducerLog> selectByExample(TradeMqProducerLogExample example);

    TradeMqProducerLog selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TradeMqProducerLog record, @Param("example") TradeMqProducerLogExample example);

    int updateByExample(@Param("record") TradeMqProducerLog record, @Param("example") TradeMqProducerLogExample example);

    int updateByPrimaryKeySelective(TradeMqProducerLog record);

    int updateByPrimaryKey(TradeMqProducerLog record);
}