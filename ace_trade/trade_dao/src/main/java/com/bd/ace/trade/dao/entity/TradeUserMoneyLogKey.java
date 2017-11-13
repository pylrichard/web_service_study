package com.bd.ace.trade.dao.entity;

import java.io.Serializable;

public class TradeUserMoneyLogKey implements Serializable{
    private Integer userId;

    private String orderId;

    private String moneyLogType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMoneyLogType() {
        return moneyLogType;
    }

    public void setMoneyLogType(String moneyLogType) {
        this.moneyLogType = moneyLogType;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moneyLogType == null) ? 0 : moneyLogType.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeUserMoneyLogKey other = (TradeUserMoneyLogKey) obj;
		if (moneyLogType == null) {
			if (other.moneyLogType != null)
				return false;
		} else if (!moneyLogType.equals(other.moneyLogType))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TradeUserMoneyLogKey [userId=" + userId + ", orderId=" + orderId + ", moneyLogType=" + moneyLogType
				+ "]";
	}
    
}