package com.bd.ace.trade.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TradeCoupon implements Serializable{
    private String couponId;

    private BigDecimal couponPrice;

    private Integer userId;

    private String orderId;

    private String isUsed;

    private Date usedTime;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

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

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((couponId == null) ? 0 : couponId.hashCode());
		result = prime * result + ((couponPrice == null) ? 0 : couponPrice.hashCode());
		result = prime * result + ((isUsed == null) ? 0 : isUsed.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((usedTime == null) ? 0 : usedTime.hashCode());
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
		TradeCoupon other = (TradeCoupon) obj;
		if (couponId == null) {
			if (other.couponId != null)
				return false;
		} else if (!couponId.equals(other.couponId))
			return false;
		if (couponPrice == null) {
			if (other.couponPrice != null)
				return false;
		} else if (!couponPrice.equals(other.couponPrice))
			return false;
		if (isUsed == null) {
			if (other.isUsed != null)
				return false;
		} else if (!isUsed.equals(other.isUsed))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (usedTime == null) {
			if (other.usedTime != null)
				return false;
		} else if (!usedTime.equals(other.usedTime))
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
		return "TradeCoupon [couponId=" + couponId + ", couponPrice=" + couponPrice + ", userId=" + userId
				+ ", orderId=" + orderId + ", isUsed=" + isUsed + ", usedTime=" + usedTime + "]";
	}
    
}