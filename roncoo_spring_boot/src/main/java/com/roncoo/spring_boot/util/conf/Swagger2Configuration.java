package com.roncoo.spring_boot.util.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class Swagger2Configuration {
    @Bean
    public Docket accessToken() {
        return new Docket(DocumentationType.SWAGGER_2)
                //定义组
                .groupName("api")
                //选择哪些路径和API生成Doc
                .select()
                //拦截的包路径
                .apis(RequestHandlerSelectors.basePackage("com.roncoo.spring_boot.controller"))
                //拦截的接口路径
                .paths(regex("/api/.*"))
                //创建
                .build()
                //配置说明
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("roncoo")
                .description("spring boot")
                .termsOfServiceUrl("http://www.roncoo.com")
                .contact(new Contact("pyl", "http://www.roncoo.com", "pylrichard@qq.com"))
                .version("1.0")
                .build();
    }
}
