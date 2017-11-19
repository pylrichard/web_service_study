package com.bd.imooc.security.core.properties;

public class BrowserProperties {
    /**
     * 默认登录页文件名
     */
    private String loginPage = "/imooc-signin.html";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
