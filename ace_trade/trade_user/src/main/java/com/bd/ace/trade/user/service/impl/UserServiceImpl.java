package com.bd.ace.trade.user.service.impl;

import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyReq;
import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyRes;
import com.bd.ace.trade.common.protocol.user.QueryUserReq;
import com.bd.ace.trade.common.protocol.user.QueryUserRes;
import com.bd.ace.trade.dao.entity.TradeUser;
import com.bd.ace.trade.dao.entity.TradeUserMoneyLog;
import com.bd.ace.trade.dao.entity.TradeUserMoneyLogExample;
import com.bd.ace.trade.dao.mapper.TradeUserMapper;
import com.bd.ace.trade.dao.mapper.TradeUserMoneyLogMapper;
import com.bd.ace.trade.user.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private TradeUserMapper tradeUserMapper;
    @Autowired
    private TradeUserMoneyLogMapper tradeUserMoneyLogMapper;

    @Override
    public QueryUserRes queryUser(QueryUserReq queryUserReq) {
        QueryUserRes queryUserRes = new QueryUserRes();
        queryUserRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
        queryUserRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        try {
            TradeUser tradeUser = tradeUserMapper.selectByPrimaryKey(queryUserReq.getUserId());
            if (tradeUser != null) {
                BeanUtils.copyProperties(tradeUser, queryUserRes);
            } else {
                throw new RuntimeException("未查询到该用户");
            }
        } catch (Exception ex) {
            queryUserRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            queryUserRes.setRetInfo(ex.getMessage());
        }
        
        return queryUserRes;
    }

    @Transactional
    @Override
    public ChangeUserMoneyRes changeUserMoney(ChangeUserMoneyReq changeUserMoneyReq) {
        ChangeUserMoneyRes changeUserMoneyRes = new ChangeUserMoneyRes();
        changeUserMoneyRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
        changeUserMoneyRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        if (changeUserMoneyReq == null || changeUserMoneyReq.getUserId() == null || changeUserMoneyReq.getUserMoney() == null) {
            throw new RuntimeException("请求参数不一致");
        }
        if (changeUserMoneyReq.getUserMoney().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("余额不能小于0");
        }
        TradeUserMoneyLog tradeUserMoneyLog = new TradeUserMoneyLog();
        tradeUserMoneyLog.setOrderId(changeUserMoneyReq.getOrderId());
        tradeUserMoneyLog.setUserId(changeUserMoneyReq.getUserId());
        tradeUserMoneyLog.setUseMoney(changeUserMoneyReq.getUserMoney());
        tradeUserMoneyLog.setCreateTime(new Date());
        tradeUserMoneyLog.setMoneyLogType(changeUserMoneyReq.getMoneyLogType());

        TradeUser tradeUser = new TradeUser();
        tradeUser.setUserId(changeUserMoneyReq.getUserId());
        tradeUser.setUserMoney(changeUserMoneyReq.getUserMoney());
        //查询是否有订单付款记录
        TradeUserMoneyLogExample tradeUserMoneyLogExample = new TradeUserMoneyLogExample();
        tradeUserMoneyLogExample.createCriteria().andUserIdEqualTo(changeUserMoneyReq.getUserId())
                .andOrderIdEqualTo(changeUserMoneyReq.getOrderId())
                .andMoneyLogTypeEqualTo(TradeEnums.UserMoneyLogTypeEnum.PAID.getCode());
        int count = this.tradeUserMoneyLogMapper.countByExample(tradeUserMoneyLogExample);
        //订单付款
        if (changeUserMoneyReq.getMoneyLogType().equals(TradeEnums.UserMoneyLogTypeEnum.PAID.getCode())) {
            if (count > 0) {
                throw new RuntimeException("已经付过款了，不能再付款");
            }
            tradeUserMapper.reduceUserMoney(tradeUser);
        }
        //订单退款
        if (changeUserMoneyReq.getMoneyLogType().equals(TradeEnums.UserMoneyLogTypeEnum.PAID.getCode())) {
            if (count == 0) {
                throw new RuntimeException("没有付款信息，不能退款");
            }
            //防止多次退款
            tradeUserMoneyLogExample = new TradeUserMoneyLogExample();
            tradeUserMoneyLogExample.createCriteria().andUserIdEqualTo(changeUserMoneyReq.getUserId())
                    .andOrderIdEqualTo(changeUserMoneyReq.getOrderId())
                    .andMoneyLogTypeEqualTo(TradeEnums.UserMoneyLogTypeEnum.REFUND.getCode());
            count = this.tradeUserMoneyLogMapper.countByExample(tradeUserMoneyLogExample);
            if (count > 0) {
                throw new RuntimeException("已经退过款了，不能退款");
            }
            tradeUserMapper.addUserMoney(tradeUser);
        }

        return changeUserMoneyRes;
    }
}
