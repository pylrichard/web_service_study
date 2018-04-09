package com.bd.ace.trade.dao.mapper;

import com.bd.ace.trade.dao.entity.TradeUserMoneyLog;
import com.bd.ace.trade.dao.entity.TradeUserMoneyLogExample;
import com.bd.ace.trade.dao.entity.TradeUserMoneyLogKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TradeUserMoneyLogMapper {
    int countByExample(TradeUserMoneyLogExample example);

    int deleteByExample(TradeUserMoneyLogExample example);

    int deleteByPrimaryKey(TradeUserMoneyLogKey key);

    int insert(TradeUserMoneyLog record);

    int insertSelective(TradeUserMoneyLog record);

    List<TradeUserMoneyLog> selectByExample(TradeUserMoneyLogExample example);

    TradeUserMoneyLog selectByPrimaryKey(TradeUserMoneyLogKey key);

    int updateByExampleSelective(@Param("record") TradeUserMoneyLog record, @Param("example") TradeUserMoneyLogExample example);

    int updateByExample(@Param("record") TradeUserMoneyLog record, @Param("example") TradeUserMoneyLogExample example);

    int updateByPrimaryKeySelective(TradeUserMoneyLog record);

    int updateByPrimaryKey(TradeUserMoneyLog record);
}