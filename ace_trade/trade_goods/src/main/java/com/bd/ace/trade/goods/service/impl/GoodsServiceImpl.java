package com.bd.ace.trade.goods.service.impl;

import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.goods.*;
import com.bd.ace.trade.dao.entity.TradeGoods;
import com.bd.ace.trade.dao.entity.TradeGoodsNumberLog;
import com.bd.ace.trade.dao.entity.TradeGoodsNumberLogKey;
import com.bd.ace.trade.dao.mapper.TradeGoodsMapper;
import com.bd.ace.trade.dao.mapper.TradeGoodsNumberLogMapper;
import com.bd.ace.trade.goods.service.IGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private TradeGoodsMapper tradeGoodsMapper;
    @Autowired
    private TradeGoodsNumberLogMapper tradeGoodsNumberLogMapper;

    @Override
    public QueryGoodsRes queryGoods(QueryGoodsReq queryGoodsReq) {
        QueryGoodsRes queryGoodsRes = new QueryGoodsRes();
        queryGoodsRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
        queryGoodsRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        try {
            TradeGoods tradeGoods = this.tradeGoodsMapper.selectByPrimaryKey(queryGoodsReq.getGoodsId());
            if (tradeGoods != null) {
                BeanUtils.copyProperties(tradeGoods, queryGoodsRes);
            } else {
                throw new Exception("未查询到商品");
            }
        } catch (Exception ex) {
            queryGoodsRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            queryGoodsRes.setRetInfo(ex.getMessage());
        }

        return queryGoodsRes;
    }

    @Transactional
    @Override
    public ReduceGoodsNumberRes reduceGoodsNumber(ReduceGoodsNumberReq reduceGoodsNumberReq) {
        ReduceGoodsNumberRes reduceGoodsNumberRes = new ReduceGoodsNumberRes();
        reduceGoodsNumberRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
        reduceGoodsNumberRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        /*
            扣减库存
         */
        TradeGoods tradeGoods = new TradeGoods();
        tradeGoods.setGoodsId(reduceGoodsNumberReq.getGoodsId());
        tradeGoods.setGoodsNumber(reduceGoodsNumberReq.getGoodsNumber());
        int record = this.tradeGoodsMapper.reduceGoodsNumber(tradeGoods);
        if (record <= 0) {
            throw new RuntimeException("扣减库存失败");
        }
        /*
            商品库存日志表插入记录
         */
        TradeGoodsNumberLog tradeGoodsNumberLog = new TradeGoodsNumberLog();
        tradeGoodsNumberLog.setGoodsId(reduceGoodsNumberReq.getGoodsId());
        tradeGoodsNumberLog.setGoodsNumber(reduceGoodsNumberReq.getGoodsNumber());
        tradeGoodsNumberLog.setOrderId(reduceGoodsNumberReq.getOrderId());
        tradeGoodsNumberLog.setLogTime(new Date());
        this.tradeGoodsNumberLogMapper.insert(tradeGoodsNumberLog);

        return reduceGoodsNumberRes;
    }

    @Override
    public AddGoodsNumberRes addGoodsNumber(AddGoodsNumberReq addGoodsNumberReq) {
        AddGoodsNumberRes addGoodsNumberRes = new AddGoodsNumberRes();
        addGoodsNumberRes.setRetCode(TradeEnums.RetEnum.SUCCESS.getCode());
        addGoodsNumberRes.setRetInfo(TradeEnums.RetEnum.SUCCESS.getDesc());
        try {
            if (addGoodsNumberReq == null || addGoodsNumberReq.getGoodsId() == null
                    || addGoodsNumberReq.getGoodsNumber() == null || addGoodsNumberReq.getGoodsNumber() <= 0) {
                throw new RuntimeException("增加库存请求参数不正确");
            }
            if (addGoodsNumberReq.getOrderId() != null) {
                TradeGoodsNumberLogKey key = new TradeGoodsNumberLogKey();
                key.setGoodsId(addGoodsNumberReq.getGoodsId());
                key.setOrderId(addGoodsNumberReq.getOrderId());
                TradeGoodsNumberLog tradeGoodsNumberLog = this.tradeGoodsNumberLogMapper.selectByPrimaryKey(key);
                if (tradeGoodsNumberLog == null) {
                    throw new RuntimeException("未找到扣库存记录");
                }
            }
            TradeGoods tradeGoods = new TradeGoods();
            tradeGoods.setGoodsId(addGoodsNumberReq.getGoodsId());
            tradeGoods.setGoodsNumber(addGoodsNumberReq.getGoodsNumber());
            int record = this.tradeGoodsMapper.addGoodsNumber(tradeGoods);
            if (record <= 0) {
                throw new RuntimeException("增加库存失败");
            }
        } catch (Exception e) {
            addGoodsNumberRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            addGoodsNumberRes.setRetInfo(e.getMessage());
        }

        return addGoodsNumberRes;
    }
}
