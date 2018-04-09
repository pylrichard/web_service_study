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
public class TradeUser implements Serializable{
    private Integer userId;

    private String userName;

    private String userPassword;

    private String userMobile;

    private Integer userScore;

    private Date userRegTime;

    private BigDecimal userMoney;
}