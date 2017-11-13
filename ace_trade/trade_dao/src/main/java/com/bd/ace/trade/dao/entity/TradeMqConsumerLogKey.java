package com.bd.ace.trade.dao.entity;

import java.io.Serializable;

public class TradeMqConsumerLogKey implements Serializable{
    private String groupName;

    private String msgTag;

    private String msgKey;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMsgTag() {
        return msgTag;
    }

    public void setMsgTag(String msgTag) {
        this.msgTag = msgTag;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }
}