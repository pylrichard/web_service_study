package com.bd.roncoo.book.shop.db;

import com.bd.roncoo.book.shop.db.support.BookShopRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication表示会扫描com.bd.roncoo.book.shop.db包下的组件，组成一个Spring Bean容器
@SpringBootApplication
//通知Spring Data JPA基于BookShopRepositoryImpl生成相应的代理对象
@EnableJpaRepositories(repositoryBaseClass = BookShopRepositoryImpl.class)
public class DbApplication {
    public static void main(String[] args) {
        //通知Spring，DbApplication作为程序入口
        SpringApplication.run(DbApplication.class, args);
    }
}