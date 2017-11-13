package com.bd.ace.trade.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TradeUser implements Serializable{
    private Integer userId;

    private String userName;

    private String userPassword;

    private String userMobile;

    private Integer userScore;

    private Date userRegTime;

    private BigDecimal userMoney;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Date getUserRegTime() {
        return userRegTime;
    }

    public void setUserRegTime(Date userRegTime) {
        this.userRegTime = userRegTime;
    }

    public BigDecimal getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userMobile == null) ? 0 : userMobile.hashCode());
		result = prime * result + ((userMoney == null) ? 0 : userMoney.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userPassword == null) ? 0 : userPassword.hashCode());
		result = prime * result + ((userRegTime == null) ? 0 : userRegTime.hashCode());
		result = prime * result + ((userScore == null) ? 0 : userScore.hashCode());
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
		TradeUser other = (TradeUser) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userMobile == null) {
			if (other.userMobile != null)
				return false;
		} else if (!userMobile.equals(other.userMobile))
			return false;
		if (userMoney == null) {
			if (other.userMoney != null)
				return false;
		} else if (!userMoney.equals(other.userMoney))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPassword == null) {
			if (other.userPassword != null)
				return false;
		} else if (!userPassword.equals(other.userPassword))
			return false;
		if (userRegTime == null) {
			if (other.userRegTime != null)
				return false;
		} else if (!userRegTime.equals(other.userRegTime))
			return false;
		if (userScore == null) {
			if (other.userScore != null)
				return false;
		} else if (!userScore.equals(other.userScore))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TradeUser [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword
				+ ", userMobile=" + userMobile + ", userScore=" + userScore + ", userRegTime=" + userRegTime
				+ ", userMoney=" + userMoney + "]";
	}
    
}