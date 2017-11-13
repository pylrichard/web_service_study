package com.bd.ace.trade.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TradeGoodsNumberLog extends TradeGoodsNumberLogKey implements Serializable{
    private Integer goodsNumber;

    private Date logTime;

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goodsNumber == null) ? 0 : goodsNumber.hashCode());
		result = prime * result + ((logTime == null) ? 0 : logTime.hashCode());
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
		TradeGoodsNumberLog other = (TradeGoodsNumberLog) obj;
		if (goodsNumber == null) {
			if (other.goodsNumber != null)
				return false;
		} else if (!goodsNumber.equals(other.goodsNumber))
			return false;
		if (logTime == null) {
			if (other.logTime != null)
				return false;
		} else if (!logTime.equals(other.logTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TradeGoodsNumberLog [goodsNumber=" + goodsNumber + ", logTime=" + logTime + "]";
	}
    
}