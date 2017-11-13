package com.bd.ace.trade.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TradeUserMoneyLog extends TradeUserMoneyLogKey implements Serializable {
    private BigDecimal useMoney;

    private Date createTime;

    public BigDecimal getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(BigDecimal useMoney) {
        this.useMoney = useMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((useMoney == null) ? 0 : useMoney.hashCode());
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
		TradeUserMoneyLog other = (TradeUserMoneyLog) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (useMoney == null) {
			if (other.useMoney != null)
				return false;
		} else if (!useMoney.equals(other.useMoney))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TradeUserMoneyLog [useMoney=" + useMoney + ", createTime=" + createTime + "]";
	}
    
	
}