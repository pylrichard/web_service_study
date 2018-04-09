package com.bd.ace.trade.goods.service;

import com.bd.ace.trade.common.protocol.goods.*;

public interface IGoodsService {
    QueryGoodsRes queryGoods(QueryGoodsReq queryGoodsReq);
    ReduceGoodsNumberRes reduceGoodsNumber(ReduceGoodsNumberReq reduceGoodsNumberReq);
    AddGoodsNumberRes addGoodsNumber(AddGoodsNumberReq addGoodsNumberReq);
}
