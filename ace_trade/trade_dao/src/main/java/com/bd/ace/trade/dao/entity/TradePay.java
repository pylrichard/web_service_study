package com.bd.ace.trade.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TradePay implements Serializable{
    private String payId;

    private String orderId;

    private BigDecimal payAmount;

    private String isPaid;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isPaid == null) ? 0 : isPaid.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((payAmount == null) ? 0 : payAmount.hashCode());
		result = prime * result + ((payId == null) ? 0 : payId.hashCode());
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
		TradePay other = (TradePay) obj;
		if (isPaid == null) {
			if (other.isPaid != null)
				return false;
		} else if (!isPaid.equals(other.isPaid))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (payAmount == null) {
			if (other.payAmount != null)
				return false;
		} else if (!payAmount.equals(other.payAmount))
			return false;
		if (payId == null) {
			if (other.payId != null)
				return false;
		} else if (!payId.equals(other.payId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TradePay [payId=" + payId + ", orderId=" + orderId + ", payAmount=" + payAmount + ", isPaid=" + isPaid
				+ "]";
	}
    
}