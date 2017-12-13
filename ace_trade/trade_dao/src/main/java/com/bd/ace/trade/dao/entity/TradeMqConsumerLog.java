package com.bd.ace.trade.dao.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeMqConsumerLog extends TradeMqConsumerLogKey {
    private String msgId;

    private String msgBody;

    private String consumerStatus;

    private Integer consumerTimes;

    private String remark;
}