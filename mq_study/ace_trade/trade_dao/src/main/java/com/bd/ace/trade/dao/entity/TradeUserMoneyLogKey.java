package com.bd.ace.trade.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TradeUserMoneyLogKey implements Serializable{
    private Integer userId;

    private String orderId;

    private String moneyLogType;
}