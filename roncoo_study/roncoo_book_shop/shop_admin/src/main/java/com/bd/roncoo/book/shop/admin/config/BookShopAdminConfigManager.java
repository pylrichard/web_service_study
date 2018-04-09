package com.bd.roncoo.book.shop.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "shop_admin.config")
public class BookShopAdminConfigManager {
    /**
     * 动态配置，事先不知道配置文件中会配置多少个users配置项
     */
    private Map<String, String> users = new HashMap<>();
    /**
     * 静态配置，需要相应类有与配置文件配置项对应的成员变量
     */
    private Class1 class1 = new Class1();
    private Class2 class2 = new Class2();
}
