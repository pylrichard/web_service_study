package com.bd.ace.trade.common.api;

import com.bd.ace.trade.common.protocol.goods.QueryGoodsReq;
import com.bd.ace.trade.common.protocol.goods.QueryGoodsRes;
import com.bd.ace.trade.common.protocol.goods.ReduceGoodsNumberReq;
import com.bd.ace.trade.common.protocol.goods.ReduceGoodsNumberRes;

public interface IGoodsApi {
    QueryGoodsRes queryGoods(QueryGoodsReq queryGoodsReq);
    ReduceGoodsNumberRes reduceGoodsNumber(ReduceGoodsNumberReq reduceGoodsNumberReq);
}
