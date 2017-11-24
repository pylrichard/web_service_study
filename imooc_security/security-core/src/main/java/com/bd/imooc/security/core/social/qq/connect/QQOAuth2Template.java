package com.bd.imooc.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class QQOAuth2Template extends OAuth2Template {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authenticateUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authenticateUrl, accessTokenUrl);
        //OAuth要求包含client_id和client_secret，见OAuth2Template.exchangeForAccess()
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        //postForAccessGrant默认实现返回Map，QQ返回的是&分隔的String，见QQ互联文档
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("获取accessToke的响应：" + responseStr);
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        String accessToken = StringUtils.substringAfter(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfter(items[1], "="));
        String refreshToken = StringUtils.substringAfter(items[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        //OAuth2Template中没有处理text/html格式响应的转换器，自行添加
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;
    }
}
