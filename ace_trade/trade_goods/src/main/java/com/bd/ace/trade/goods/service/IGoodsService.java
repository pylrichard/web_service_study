package com.bd.ace.trade.goods.service;

import com.bd.ace.trade.common.protocol.goods.AddGoodsNumberReq;
import com.bd.ace.trade.common.protocol.goods.AddGoodsNumberRes;
import com.bd.ace.trade.common.protocol.goods.QueryGoodsReq;
import com.bd.ace.trade.common.protocol.goods.QueryGoodsRes;
import com.bd.ace.trade.common.protocol.goods.ReduceGoodsNumberReq;
import com.bd.ace.trade.common.protocol.goods.ReduceGoodsNumberRes;

public interface IGoodsService {
    QueryGoodsRes queryGoods(QueryGoodsReq queryGoodsReq);
    ReduceGoodsNumberRes reduceGoodsNumber(ReduceGoodsNumberReq reduceGoodsNumberReq);
    AddGoodsNumberRes addGoodsNumber(AddGoodsNumberReq addGoodsNumberReq);
}
