package com.bd.ace.trade.common.client;

import com.bd.ace.trade.common.protocol.user.QueryUserReq;
import com.bd.ace.trade.common.protocol.user.QueryUserRes;
import org.springframework.web.client.RestTemplate;

public class RestClient {
	private static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		QueryUserReq queryUserReq = new QueryUserReq();
		queryUserReq.setUserId(1);
		QueryUserRes queryUserRes = restTemplate.postForObject("http://localhost:8080/user/queryUser",
															queryUserReq, QueryUserRes.class);
		System.out.println(queryUserRes);
	}
}
