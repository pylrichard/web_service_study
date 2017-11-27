package com.bd.imooc.security.core.social.weixin.connect;

import com.bd.imooc.security.core.social.weixin.api.Weixin;
import com.bd.imooc.security.core.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * API适配器将微信API的数据模型转为Spring Social的标准模型
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {
	private String openId;
	
	public WeixinAdapter() {}
	
	public WeixinAdapter(String openId){
		this.openId = openId;
	}

	@Override
	public boolean test(Weixin api) {
		return true;
	}

	@Override
	public void setConnectionValues(Weixin api, ConnectionValues values) {
		WeixinUserInfo profile = api.getUserInfo(openId);
		values.setProviderUserId(profile.getOpenid());
		values.setDisplayName(profile.getNickname());
		values.setImageUrl(profile.getHeadimgurl());
	}

	@Override
	public UserProfile fetchUserProfile(Weixin api) {
		return null;
	}

	@Override
	public void updateStatus(Weixin api, String message) {}
}
