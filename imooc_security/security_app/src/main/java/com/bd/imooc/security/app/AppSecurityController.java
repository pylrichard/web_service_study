package com.bd.imooc.security.app;

import com.bd.imooc.security.app.social.AppSignUpUtils;
import com.bd.imooc.security.core.properties.SecurityConstants;
import com.bd.imooc.security.core.social.SocialController;
import com.bd.imooc.security.core.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AppSecurityController extends SocialController {
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSignUpUtils appSignUpUtils;

    /**
     * 需要注册时跳到这里，返回401(前端引导用户去注册，发送/signUp请求到UserController)和用户信息给前端
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        //保存第三方用户信息到Redis
        appSignUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());

        return buildSocialUserInfo(connection);
    }
}
