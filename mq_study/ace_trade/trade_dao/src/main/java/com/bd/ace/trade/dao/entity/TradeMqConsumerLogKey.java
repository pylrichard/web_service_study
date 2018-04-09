package com.bd.ace.trade.dao.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TradeMqConsumerLogKey implements Serializable{
    private String groupName;

    private String msgTag;

    private String msgKey;
}