package com.bd.imooc.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码信息封装类
 *
 * 实现分布式Session，ValidateCode要存储到Redis中，需要被序列化
 */
public class ValidateCode implements Serializable {
    private static final long serialVersionUID = 1588203828504660915L;
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
