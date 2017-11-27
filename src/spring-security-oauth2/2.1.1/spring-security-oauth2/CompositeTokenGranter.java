package org.springframework.security.oauth2.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class CompositeTokenGranter implements TokenGranter {
    /**
     * 产生令牌的5种模式 = 4种授权模式 + 刷新令牌模式
     */
    private final List<TokenGranter> tokenGranters;

    public CompositeTokenGranter(List<TokenGranter> tokenGranters) {
        this.tokenGranters = new ArrayList<TokenGranter>(tokenGranters);
    }

    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        for (TokenGranter granter : tokenGranters) {
            //调用AbstractTokenGranter.grant()
            OAuth2AccessToken grant = granter.grant(grantType, tokenRequest);
            if (grant != null) {
                return grant;
            }
        }
        return null;
    }

    public void addTokenGranter(TokenGranter tokenGranter) {
        if (tokenGranter == null) {
            throw new IllegalArgumentException("Token granter is null");
        }
        tokenGranters.add(tokenGranter);
    }
}
