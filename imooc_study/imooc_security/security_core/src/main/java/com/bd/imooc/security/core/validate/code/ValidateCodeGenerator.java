package com.bd.imooc.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码生成器
 */
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
