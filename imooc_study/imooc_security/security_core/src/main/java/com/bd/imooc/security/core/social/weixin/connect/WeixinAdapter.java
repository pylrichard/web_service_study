package com.bd.imooc.security.core.social.weixin.connect;

import com.bd.imooc.security.core.social.weixin.api.Weixin;
import com.bd.imooc.security.core.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

/**
 * API适配器将微信API的数据模型转为Spring Social的标准模型
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {
    private String openId;

    public WeixinAdapter() {
    }

    public WeixinAdapter(String openId) {
        this.openId = openId;
    }

    /**
     * 验证服务是否可用，一般调用服务看是否发生异常，此处简化
     */
    @Override
    public boolean test(Weixin api) {
        return true;
    }

    /**
     * 将调用第三方应用服务返回的用户信息JSON结果映射到UserConnection表的对应字段
     */
    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo profile = api.getUserInfo(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        WeixinUserInfo userProfile = api.getUserInfo(openId);
        return new UserProfileBuilder().setName(userProfile.getNickname()).build();
    }

    @Override
    public void updateStatus(Weixin api, String message) {
    }
}
