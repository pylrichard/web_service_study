package com.bd.imooc.security.core.properties;

public class BrowserProperties {
    private SessionProperties session = new SessionProperties();
    /**
     * 社交登录如果需要用户注册进行跳转的页面
     */
    private String signUpUrl = "/imooc-signup.html";
    /**
     * 退出成功时跳转的url，如果配置了则跳到指定的url，如果没配置则返回json数据
     */
    private String signOutUrl;
    /**
     * 登录页面，当引发登录行为的url以html结尾时，会跳转到此url
     */
    private String signinPage = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;
    /**
     * 登录响应的方式，默认是json
     */
    private SignInResponseType signInResponseType = SignInResponseType.JSON;
    /**
     * 记住我功能的有效时间，默认1小时
     */
    private int rememberMeSeconds = 3600;
    /**
     * 登录成功后跳转的地址，如果设置此属性，则登录成功后会跳转到这个地址
     * 只在signInResponseType为REDIRECT时生效
     */
    private String singInSuccessUrl;

    public String getSigninPage() {
        return signinPage;
    }

    public void setSigninPage(String signinPage) {
        this.signinPage = signinPage;
    }

    public SignInResponseType getSignInResponseType() {
        return signInResponseType;
    }

    public void setSignInResponseType(SignInResponseType signInResponseType) {
        this.signInResponseType = signInResponseType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    public String getSingInSuccessUrl() {
        return singInSuccessUrl;
    }

    public void setSingInSuccessUrl(String singInSuccessUrl) {
        this.singInSuccessUrl = singInSuccessUrl;
    }
}
