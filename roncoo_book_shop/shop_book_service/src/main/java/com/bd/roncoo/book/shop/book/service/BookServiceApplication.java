package com.bd.roncoo.book.shop.book.service;

import com.bd.roncoo.book.shop.db.support.BookShopRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication表示会扫描com.bd.roncoo.book.shop.book.service包下的组件，组成一个Spring Bean容器
@SpringBootApplication
@ImportResource("classpath:provider.xml")
@EnableCaching
//@EnableScheduling
//通知Spring Data JPA基于BookShopRepositoryImpl生成相应的代理对象
@EnableJpaRepositories(repositoryBaseClass = BookShopRepositoryImpl.class)
public class BookServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BookServiceApplication.class);
        app.setAdditionalProfiles("pro");
        app.run(args);
    }
}
