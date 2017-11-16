package com.bd.roncoo.book.shop.admin.controller;

import com.bd.roncoo.book.shop.dto.BookInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {
    /**
     * 注解@RequestParam的required()默认是true，不传参会报错，设置了defaultValue就不会报错了
     */
    @GetMapping("/book")
    public List<BookInfo> query(@RequestParam(value = "name", defaultValue = "hello spring") String bookName) {
        System.out.println(bookName);
        List<BookInfo> bookInfos = new ArrayList<>();
        bookInfos.add(new BookInfo());
        bookInfos.add(new BookInfo());
        bookInfos.add(new BookInfo());

        return bookInfos;
    }
}
