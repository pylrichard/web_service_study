package com.bd.ace.trade.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TradeUserMoneyLog extends TradeUserMoneyLogKey implements Serializable {
    private BigDecimal useMoney;

    private Date createTime;
}