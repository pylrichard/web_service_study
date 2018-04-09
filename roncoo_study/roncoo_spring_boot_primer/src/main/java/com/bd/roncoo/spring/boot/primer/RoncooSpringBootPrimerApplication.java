package com.bd.roncoo.spring.boot.primer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//让Spring扫描到Servlet和Filter
@ServletComponentScan
@EnableCaching
@EnableJms
//浏览器输入http://localhost:8080/swagger-ui.html
@EnableSwagger2
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
@ImportResource(locations = { "classpath:druid-bean.xml" })
public class RoncooSpringBootPrimerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoncooSpringBootPrimerApplication.class, args);
    }
}
