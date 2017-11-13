package com.bd.ace.trade.dao.mapper;

import com.bd.ace.trade.dao.entity.TradeMqConsumerLogKey;
import com.bd.ace.trade.dao.entity.TradeMqConsumerLog;
import com.bd.ace.trade.dao.entity.TradeMqConsumerLogExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TradeMqConsumerLogMapper {
    long countByExample(TradeMqConsumerLogExample example);

    int deleteByExample(TradeMqConsumerLogExample example);

    int deleteByPrimaryKey(TradeMqConsumerLogKey key);

    int insert(TradeMqConsumerLog record);

    int insertSelective(TradeMqConsumerLog record);

    List<TradeMqConsumerLog> selectByExample(TradeMqConsumerLogExample example);

    TradeMqConsumerLog selectByPrimaryKey(TradeMqConsumerLogKey key);

    int updateByExampleSelective(@Param("record") TradeMqConsumerLog record, @Param("example") TradeMqConsumerLogExample example);

    int updateByExample(@Param("record") TradeMqConsumerLog record, @Param("example") TradeMqConsumerLogExample example);

    int updateByPrimaryKeySelective(TradeMqConsumerLog record);

    int updateByPrimaryKey(TradeMqConsumerLog record);
}