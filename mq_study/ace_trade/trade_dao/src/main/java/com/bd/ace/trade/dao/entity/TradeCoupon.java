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
public class TradeCoupon implements Serializable{
    private String couponId;

    private BigDecimal couponPrice;

    private Integer userId;

    private String orderId;

    private String isUsed;

    private Date usedTime;
}