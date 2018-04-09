package com.bd.ace.trade.dao.mapper;

import com.bd.ace.trade.dao.entity.TradeUser;
import com.bd.ace.trade.dao.entity.TradeUserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TradeUserMapper {
    int countByExample(TradeUserExample example);

    int deleteByExample(TradeUserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(TradeUser record);

    int insertSelective(TradeUser record);

    List<TradeUser> selectByExample(TradeUserExample example);

    TradeUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") TradeUser record, @Param("example") TradeUserExample example);

    int updateByExample(@Param("record") TradeUser record, @Param("example") TradeUserExample example);

    int updateByPrimaryKeySelective(TradeUser record);

    int updateByPrimaryKey(TradeUser record);

    /**
     * 扣用户余额
     */
    int reduceUserMoney(TradeUser record);

    /**
     * 加用户余额
     */
    int addUserMoney(TradeUser record);
}