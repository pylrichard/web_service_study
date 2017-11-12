package com.bd.roncoo.book.shop.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication表示会扫描com.bd.book_shop.db包下的组件，组成一个Spring Bean容器
@SpringBootApplication
public class DbApplication {
    public static void main(String[] args) {
        //通知Spring，DBApplication作为程序入口
        SpringApplication.run(DbApplication.class, args);
    }
}