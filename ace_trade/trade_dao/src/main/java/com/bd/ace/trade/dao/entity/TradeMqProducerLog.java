package com.bd.ace.trade.dao.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TradeMqProducerLog {
    private String id;

    private String groupName;

    private String msgTopic;

    private String msgTag;

    private String msgKeys;

    private String msgBody;

    private Date createTime;
}