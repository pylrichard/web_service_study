package com.bd.ace.trade.common.protocol.goods;

import com.bd.ace.trade.common.protocol.BaseRes;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class QueryGoodsRes extends BaseRes {
    private Integer goodsId;
    private String goodsName;
    private Integer goodsNumber;
    private BigDecimal goodsPrice;
    private String goodsDesc;
    private Date addTime;
}
