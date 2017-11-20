package com.bd.imooc.security.example.code;

import com.bd.imooc.security.core.validate.code.ImageCode;
import com.bd.imooc.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("validateCodeGenerator")
public class ImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("更高级的图形验证码生成代码");

        return null;
    }
}
