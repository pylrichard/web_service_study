package com.bd.ace.trade.common.protocol.goods;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReduceGoodsNumberReq {
    private Integer goodsId;
    private Integer goodsNumber;
    private String orderId;
}
