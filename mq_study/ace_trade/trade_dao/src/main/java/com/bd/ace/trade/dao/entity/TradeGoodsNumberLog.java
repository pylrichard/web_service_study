package com.bd.ace.trade.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TradeGoodsNumberLog extends TradeGoodsNumberLogKey implements Serializable {
    private Integer goodsNumber;

    private Date logTime;
}