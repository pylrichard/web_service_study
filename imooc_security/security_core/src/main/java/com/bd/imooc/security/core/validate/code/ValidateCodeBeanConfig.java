package com.bd.imooc.security.core.validate.code;

import com.bd.imooc.security.core.properties.SecurityProperties;
import com.bd.imooc.security.core.validate.code.img.ImageCodeGenerator;
import com.bd.imooc.security.core.validate.code.mobile.DefaultSmsCodeSender;
import com.bd.imooc.security.core.validate.code.mobile.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码相关扩展配置。配置在这里的bean，业务系统可以通过声明同类型或同名的bean覆盖默认的配置
 */
@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 图片验证码图片生成器
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);

        return codeGenerator;
    }

    /**
     * 短信验证码发送器
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
