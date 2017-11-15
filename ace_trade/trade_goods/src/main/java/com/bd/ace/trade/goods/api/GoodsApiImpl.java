package com.bd.ace.trade.goods.api;

import com.bd.ace.trade.common.api.IGoodsApi;
import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.goods.QueryGoodsReq;
import com.bd.ace.trade.common.protocol.goods.QueryGoodsRes;
import com.bd.ace.trade.common.protocol.goods.ReduceGoodsNumberReq;
import com.bd.ace.trade.common.protocol.goods.ReduceGoodsNumberRes;
import com.bd.ace.trade.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoodsApiImpl implements IGoodsApi {
    @Autowired
    private IGoodsService goodsService;

    @Override
    @PostMapping("/queryGoods")
    public QueryGoodsRes queryGoods(@RequestBody QueryGoodsReq queryGoodsReq) {
        if (queryGoodsReq == null || queryGoodsReq.getGoodsId() == null) {
            throw new RuntimeException("查询商品信息ID不正确");
        }

        return this.goodsService.queryGoods(queryGoodsReq);
    }

    @Override
    public ReduceGoodsNumberRes reduceGoodsNumber(@RequestBody ReduceGoodsNumberReq reduceGoodsNumberReq) {
        if (reduceGoodsNumberReq == null || reduceGoodsNumberReq.getGoodsId() == null
                || reduceGoodsNumberReq.getGoodsNumber() == null || reduceGoodsNumberReq.getGoodsNumber() <= 0) {
            throw new RuntimeException("扣减库存请求参数不正确");
        }
        ReduceGoodsNumberRes reduceGoodsNumberRes = new ReduceGoodsNumberRes();
        try {
            reduceGoodsNumberRes = this.goodsService.reduceGoodsNumber(reduceGoodsNumberReq);
        } catch (Exception e) {
            reduceGoodsNumberRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            reduceGoodsNumberRes.setRetInfo(e.getMessage());
        }

        return reduceGoodsNumberRes;
    }
}
