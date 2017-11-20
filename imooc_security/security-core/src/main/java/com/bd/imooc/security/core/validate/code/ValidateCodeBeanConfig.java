package com.bd.imooc.security.core.validate.code;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {
    @Bean
    public ValidateCodeGenerator imageCodeGenerator() {
        return new DefaultImageCodeGenerator();
    }
}
