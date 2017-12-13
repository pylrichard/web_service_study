package com.bd.ace.trade.common.protocol.user;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChangeUserMoneyReq {
    private Integer userId;
    private BigDecimal userMoney;
    private String moneyLogType;
    private String orderId;
}
