package com.bd.roncoo.book.shop.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Setter
@Component
public class BookShopAdminConfig implements InitializingBean {
    /**
     * application.properties中没有配置shop_admin.config.name，使用默认值book_shop-admin
     * 这种配置方法不够灵活
     */
    @Value("${shop_admin.config.name:shop_admin}")
    private String name;

    @Autowired
    private BookShopAdminConfigManager configManager;

    /**
     * 此方法在本类对象组装完毕后调用
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(name);
        System.out.println(ReflectionToStringBuilder.toString(configManager, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(configManager.getClass1().getName());
        System.out.println(configManager.getClass2().getName());

        Set<String> keys = configManager.getUsers().keySet();
        for (String key : keys) {
            System.out.println(key);
            System.out.println(configManager.getUsers().get(key));
        }
    }
}
