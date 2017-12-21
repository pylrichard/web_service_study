package com.bd.imooc.spring.bean.annotation.java.based;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 见48~.png
 */
@Configuration
@ImportResource("classpath:config.xml")
public class StoreConfig {
    @Value("${url}")
    private String url;

    /**
     * 注意此处使用username，会获取到OS的用户名，见50.png
     */
    @Value("${jdbc.user_name}")
    private String userName;

    @Value("${password}")
    private String password;

    @Bean
    public MyDriverManager myDriverManager() {
        return new MyDriverManager(url, userName, password);
    }

    /**
     * 指定初始化/销毁方法，见49.png
     */
//	@Bean(name = "stringStore", initMethod="init", destroyMethod="destroy")
//	public Store stringStore() {
//		return new StringStore();
//	}

    /**
     * 对应testScope()
     */
//	@Bean(name = "stringStore")
//	@Scope(value="prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
//	public Store stringStore() {
//		return new StringStore();
//	}

    /**
     * 基于泛型的自动装配，见53.png
     */
    @Autowired
    private Store<String> s1;

    @Autowired
    private Store<Integer> s2;

    /**
     * 不指定Bean名称，默认是方法名
     */
    @Bean
    public StringStore stringStore() {
        return new StringStore();
    }

    @Bean
    public IntegerStore integerStore() {
        return new IntegerStore();
    }

    /**
     * 返回值为StringStore，自动装配发现有2个可以赋值给s1的Bean，会报错
     */
    @Bean(name = "stringStoreTest")
    public Store stringStoreTest() {
        System.out.println("s1 : " + s1.getClass().getName());
        System.out.println("s2 : " + s2.getClass().getName());

        return new StringStore();
    }
}
