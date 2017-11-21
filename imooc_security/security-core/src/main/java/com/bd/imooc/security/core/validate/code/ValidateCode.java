package com.bd.imooc.security.core.validate.code;

import java.time.LocalDateTime;

public class ValidateCode {
    private String code;
    /**
     * 过期时间点，判断是否过期使用日期对象进行比较
     */
    private LocalDateTime expireTime;

    /**
     * @param expireIn 过期秒数，比如60s
     */
    public ValidateCode(String code, int expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime){
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
