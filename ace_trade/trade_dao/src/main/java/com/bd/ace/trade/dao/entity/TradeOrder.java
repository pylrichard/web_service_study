package com.bd.ace.trade.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TradeOrder implements Serializable{
    private String orderId;

    private Integer userId;

    private String orderStatus;

    private String payStatus;

    private String shippingStatus;

    private String address;

    private String consignee;

    private Integer goodsId;

    private Integer goodsNumber;

    private BigDecimal goodsPrice;

    private BigDecimal goodsAmount;

    private BigDecimal shippingFee;

    private BigDecimal orderAmount;

    private String couponId;

    private BigDecimal couponPaid;

    private BigDecimal moneyPaid;

    private BigDecimal payAmount;

    private Date createTime;

    private Date confirmTime;

    private Date payTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(BigDecimal goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getCouponPaid() {
        return couponPaid;
    }

    public void setCouponPaid(BigDecimal couponPaid) {
        this.couponPaid = couponPaid;
    }

    public BigDecimal getMoneyPaid() {
        return moneyPaid;
    }

    public void setMoneyPaid(BigDecimal moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((confirmTime == null) ? 0 : confirmTime.hashCode());
		result = prime * result + ((consignee == null) ? 0 : consignee.hashCode());
		result = prime * result + ((couponId == null) ? 0 : couponId.hashCode());
		result = prime * result + ((couponPaid == null) ? 0 : couponPaid.hashCode());
		result = prime * result + ((goodsAmount == null) ? 0 : goodsAmount.hashCode());
		result = prime * result + ((goodsId == null) ? 0 : goodsId.hashCode());
		result = prime * result + ((goodsNumber == null) ? 0 : goodsNumber.hashCode());
		result = prime * result + ((goodsPrice == null) ? 0 : goodsPrice.hashCode());
		result = prime * result + ((moneyPaid == null) ? 0 : moneyPaid.hashCode());
		result = prime * result + ((orderAmount == null) ? 0 : orderAmount.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result + ((payAmount == null) ? 0 : payAmount.hashCode());
		result = prime * result + ((payStatus == null) ? 0 : payStatus.hashCode());
		result = prime * result + ((payTime == null) ? 0 : payTime.hashCode());
		result = prime * result + ((shippingFee == null) ? 0 : shippingFee.hashCode());
		result = prime * result + ((shippingStatus == null) ? 0 : shippingStatus.hashCode());
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
		TradeOrder other = (TradeOrder) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (confirmTime == null) {
			if (other.confirmTime != null)
				return false;
		} else if (!confirmTime.equals(other.confirmTime))
			return false;
		if (consignee == null) {
			if (other.consignee != null)
				return false;
		} else if (!consignee.equals(other.consignee))
			return false;
		if (couponId == null) {
			if (other.couponId != null)
				return false;
		} else if (!couponId.equals(other.couponId))
			return false;
		if (couponPaid == null) {
			if (other.couponPaid != null)
				return false;
		} else if (!couponPaid.equals(other.couponPaid))
			return false;
		if (goodsAmount == null) {
			if (other.goodsAmount != null)
				return false;
		} else if (!goodsAmount.equals(other.goodsAmount))
			return false;
		if (goodsId == null) {
			if (other.goodsId != null)
				return false;
		} else if (!goodsId.equals(other.goodsId))
			return false;
		if (goodsNumber == null) {
			if (other.goodsNumber != null)
				return false;
		} else if (!goodsNumber.equals(other.goodsNumber))
			return false;
		if (goodsPrice == null) {
			if (other.goodsPrice != null)
				return false;
		} else if (!goodsPrice.equals(other.goodsPrice))
			return false;
		if (moneyPaid == null) {
			if (other.moneyPaid != null)
				return false;
		} else if (!moneyPaid.equals(other.moneyPaid))
			return false;
		if (orderAmount == null) {
			if (other.orderAmount != null)
				return false;
		} else if (!orderAmount.equals(other.orderAmount))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (orderStatus == null) {
			if (other.orderStatus != null)
				return false;
		} else if (!orderStatus.equals(other.orderStatus))
			return false;
		if (payAmount == null) {
			if (other.payAmount != null)
				return false;
		} else if (!payAmount.equals(other.payAmount))
			return false;
		if (payStatus == null) {
			if (other.payStatus != null)
				return false;
		} else if (!payStatus.equals(other.payStatus))
			return false;
		if (payTime == null) {
			if (other.payTime != null)
				return false;
		} else if (!payTime.equals(other.payTime))
			return false;
		if (shippingFee == null) {
			if (other.shippingFee != null)
				return false;
		} else if (!shippingFee.equals(other.shippingFee))
			return false;
		if (shippingStatus == null) {
			if (other.shippingStatus != null)
				return false;
		} else if (!shippingStatus.equals(other.shippingStatus))
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
		return "TradeOrder [orderId=" + orderId + ", userId=" + userId + ", orderStatus=" + orderStatus + ", payStatus="
				+ payStatus + ", shippingStatus=" + shippingStatus + ", address=" + address + ", consignee=" + consignee
				+ ", goodsId=" + goodsId + ", goodsNumber=" + goodsNumber + ", goodsPrice=" + goodsPrice
				+ ", goodsAmount=" + goodsAmount + ", shippingFee=" + shippingFee + ", orderAmount=" + orderAmount
				+ ", couponId=" + couponId + ", couponPaid=" + couponPaid + ", moneyPaid=" + moneyPaid + ", payAmount="
				+ payAmount + ", createTime=" + createTime + ", confirmTime=" + confirmTime + ", payTime=" + payTime + "]";
	}
    
}