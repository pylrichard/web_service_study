package com.bd.imooc.security.core.social.weixin.connect;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 微信的access_token与标准OAuth2协议不同，微信在获取access_token时会同时返回openId，并没有单独通过accessToken换取openId的服务
 * 
 * 这里继承AccessGrant，添加了openId字段，作为对微信access_token信息的封装
 */
@Getter
@Setter
public class WeixinAccessGrant extends AccessGrant {
	private static final long serialVersionUID = -7243374526633186782L;
	
	private String openId;
	
	public WeixinAccessGrant() {
		super("");
	}

	public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
		super(accessToken, scope, refreshToken, expiresIn);
	}
}
