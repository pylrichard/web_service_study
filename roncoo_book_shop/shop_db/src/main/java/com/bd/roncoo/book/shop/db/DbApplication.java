package com.bd.roncoo.book.shop.db;

import com.bd.roncoo.book.shop.db.repository.BookShopRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BookShopRepositoryImpl.class)
public class DbApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class, args);
    }
}