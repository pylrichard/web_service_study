package com.bd.ace.trade.common.protocol.user;

public class QueryUserReq {
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "QueryUserReq [userId=" + userId + "]";
	}
}
