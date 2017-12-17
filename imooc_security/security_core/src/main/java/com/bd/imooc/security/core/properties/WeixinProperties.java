package com.bd.imooc.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * 微信登录配置项
 */
@Getter
@Setter
public class WeixinProperties extends SocialProperties {
    private String providerId = "weixin";
}
