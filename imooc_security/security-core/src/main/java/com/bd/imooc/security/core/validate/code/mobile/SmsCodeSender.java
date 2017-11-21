package com.bd.imooc.security.core.validate.code.mobile;

public interface SmsCodeSender {
    void send(String mobile, String code);
}
