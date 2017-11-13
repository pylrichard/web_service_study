package com.bd.ace.trade.dao.mapper;

import com.bd.ace.trade.dao.entity.TradeGoodsNumberLog;
import com.bd.ace.trade.dao.entity.TradeGoodsNumberLogExample;
import com.bd.ace.trade.dao.entity.TradeGoodsNumberLogKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TradeGoodsNumberLogMapper {
    int countByExample(TradeGoodsNumberLogExample example);

    int deleteByExample(TradeGoodsNumberLogExample example);

    int deleteByPrimaryKey(TradeGoodsNumberLogKey key);

    int insert(TradeGoodsNumberLog record);

    int insertSelective(TradeGoodsNumberLog record);

    List<TradeGoodsNumberLog> selectByExample(TradeGoodsNumberLogExample example);

    TradeGoodsNumberLog selectByPrimaryKey(TradeGoodsNumberLogKey key);

    int updateByExampleSelective(@Param("record") TradeGoodsNumberLog record, @Param("example") TradeGoodsNumberLogExample example);

    int updateByExample(@Param("record") TradeGoodsNumberLog record, @Param("example") TradeGoodsNumberLogExample example);

    int updateByPrimaryKeySelective(TradeGoodsNumberLog record);

    int updateByPrimaryKey(TradeGoodsNumberLog record);
}