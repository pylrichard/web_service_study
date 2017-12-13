package com.bd.ace.trade.common.protocol.user;

import com.bd.ace.trade.common.protocol.BaseRes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class QueryUserRes extends BaseRes {
    private Integer userId;

    private String userName;

    private String userMobile;

    private Integer userScore;

    private Date userRegTime;

    private BigDecimal userMoney;
}
